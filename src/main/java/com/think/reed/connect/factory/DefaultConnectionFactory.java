package com.think.reed.connect.factory;

import com.think.reed.codec.Codec;
import com.think.reed.codec.ReedCodec;
import com.think.reed.connect.metaobject.Connection;
import com.think.reed.connect.metaobject.Url;
import com.think.reed.rpc.remoting.handler.ReedRpcHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/5 01:07
 **/
public class DefaultConnectionFactory implements ConnectionFactory {
    private static final Logger logger = LoggerFactory.getLogger(DefaultConnectionFactory.class);

    protected Bootstrap bootstrap;
    private final Codec codec;
    private final ChannelHandler handler;

    private static final EventLoopGroup workerGroup = new NioEventLoopGroup(4);

    public DefaultConnectionFactory() {
        // 默认
        this.codec = new ReedCodec();
        // 默认
        this.handler = new ReedRpcHandler();
    }

    public void initBootstrap() {
        bootstrap = new Bootstrap();
        // 暂时不引入EpollSocketChannel.class
        bootstrap.group(workerGroup).channel(NioSocketChannel.class)
            // 后期配置化
            .option(ChannelOption.TCP_NODELAY, true)
            // 后期配置化
            .option(ChannelOption.SO_REUSEADDR, true)
            // 后期配置化
            .option(ChannelOption.SO_KEEPALIVE, true);

        initWriteBufferWaterMark();

        // PooledByteBufAllocator.DEFAULT 后期使用
        this.bootstrap.option(ChannelOption.ALLOCATOR, UnpooledByteBufAllocator.DEFAULT);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel channel) {
                ChannelPipeline pipeline = channel.pipeline();
                pipeline.addLast("decoder", codec.newDecoder());
                pipeline.addLast("encoder", codec.newEncoder());
                pipeline.addLast("idleStateHandler", new IdleStateHandler(0, 0, 60));
                pipeline.addLast("handler", handler);
            }
        });
    }

    /**
     * 使用默认配置，二期优化
     */
    private void initWriteBufferWaterMark() {

    }

    @Override
    public Connection createConnection(Url url) throws Exception {
        Channel channel = doCreateConnection(url.getIp(), url.getPort(), url.getConnectTimeout());
        Connection conn = new Connection(channel);
        if (channel.isActive()) {
            channel.pipeline().fireUserEventTriggered(ConnectionEventType.CONNECT);
        } else {
            channel.pipeline().fireUserEventTriggered(ConnectionEventType.CONNECT_FAILED);
        }
        return conn;
    }

    @Override
    public Connection createConnection(String targetIP, int targetPort, int connectTimeout) throws Exception {

        Channel channel = doCreateConnection(targetIP, targetPort, connectTimeout);
        Connection conn = new Connection(channel);
        if (channel.isActive()) {
            channel.pipeline().fireUserEventTriggered(ConnectionEventType.CONNECT);
        } else {
            channel.pipeline().fireUserEventTriggered(ConnectionEventType.CONNECT_FAILED);
        }
        return conn;
    }

    protected Channel doCreateConnection(String targetIP, int targetPort, int connectTimeout) throws Exception {
        // prevent unreasonable value, at least 1000
        connectTimeout = Math.max(connectTimeout, 1000);
        String address = targetIP + ":" + targetPort;
        if (logger.isDebugEnabled()) {
            logger.debug("connectTimeout of address [{}] is [{}].", address, connectTimeout);
        }
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout);
        ChannelFuture future = bootstrap.connect(new InetSocketAddress(targetIP, targetPort));

        future.awaitUninterruptibly();
        if (!future.isDone()) {
            String errMsg = "Create connection to " + address + " timeout!";
            logger.warn(errMsg);
            throw new Exception(errMsg);
        }
        if (future.isCancelled()) {
            String errMsg = "Create connection to " + address + " cancelled by user!";
            logger.warn(errMsg);
            throw new Exception(errMsg);
        }
        if (!future.isSuccess()) {
            String errMsg = "Create connection to " + address + " error!";
            logger.warn(errMsg);
            throw new Exception(errMsg, future.cause());
        }
        return future.channel();
    }

    enum ConnectionEventType {
        /**
         * 连接
         */
        CONNECT,
        /**
         * 连接失败
         */
        CONNECT_FAILED,
        /**
         * 连接关闭
         */
        CLOSE,
        /**
         * 
         */
        EXCEPTION;
    }

}
