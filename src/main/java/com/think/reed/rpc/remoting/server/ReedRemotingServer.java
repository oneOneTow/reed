package com.think.reed.rpc.remoting.server;

import com.think.reed.codec.Codec;
import com.think.reed.codec.ReedCodec;
import com.think.reed.connect.DefaultConnectionManager;
import com.think.reed.connect.metaobject.Connection;
import com.think.reed.connect.metaobject.Url;
import com.think.reed.connect.strategy.RandomConnectionSelectStrategy;
import com.think.reed.rpc.remoting.Remoting;
import com.think.reed.rpc.remoting.command.RpcCommandType;
import com.think.reed.rpc.remoting.command.RpcCommandFactory;
import com.think.reed.rpc.remoting.handler.ReedRpcHandler;
import com.think.reed.rpc.remoting.process.RemotingProcessor;
import com.think.reed.util.UrlUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.epoll.EpollMode;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/5 21:58
 **/
public class ReedRemotingServer implements RemotingServer {

    private static final Logger logger = LoggerFactory.getLogger(ReedRemotingServer.class);

    private   AtomicBoolean started = new AtomicBoolean(false);
    private   String        ip;
    private   int           port;
    protected Remoting      rpcRemoting;

    private Codec codec = new ReedCodec();
    private DefaultConnectionManager connectionManager;
    private ServerBootstrap bootstrap;
    private ChannelFuture channelFuture;
    private static final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    /** worker event loop group. Reuse I/O worker threads between rpc servers. */
    private static final EventLoopGroup workerGroup = new NioEventLoopGroup(4);

    public ReedRemotingServer(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void init() {

    }

    @Override
    public boolean start() {
        if (started.compareAndSet(false, true)) {
            try {
                doInit();
                logger.warn("Prepare to start server on port {} ", port);
                if (doStart()) {
                    logger.warn("Server started on port {}", port);
                    return true;
                } else {
                    logger.warn("Failed starting server on port {}", port);
                    return false;
                }
            } catch (Throwable t) {
                this.stop();// do stop to ensure close resources created during doInit()
                throw new IllegalStateException("ERROR: Failed to start the Server!", t);
            }
        } else {
            String errMsg = "ERROR: The server has already started!";
            logger.error(errMsg);
            throw new IllegalStateException(errMsg);
        }
    }

    protected void doInit() {
        this.connectionManager = new DefaultConnectionManager(new RandomConnectionSelectStrategy());
        this.rpcRemoting = new ReedServerRemoting(this.connectionManager, new RpcCommandFactory());
        this.bootstrap = new ServerBootstrap();
        this.bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
            .option(ChannelOption.SO_BACKLOG, 1024).option(ChannelOption.SO_REUSEADDR, true)
            .childOption(ChannelOption.TCP_NODELAY, true).childOption(ChannelOption.SO_KEEPALIVE, true);

        initWriteBufferWaterMark();

        this.bootstrap.option(ChannelOption.ALLOCATOR, UnpooledByteBufAllocator.DEFAULT)
            .childOption(ChannelOption.ALLOCATOR, UnpooledByteBufAllocator.DEFAULT);

        bootstrap.childOption(EpollChannelOption.EPOLL_MODE, EpollMode.EDGE_TRIGGERED);

        final ChannelHandler handler = new ReedRpcHandler(true);
        this.bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel channel) {
                ChannelPipeline pipeline = channel.pipeline();
                pipeline.addLast("decoder", codec.newDecoder());
                pipeline.addLast("encoder", codec.newEncoder());
                pipeline.addLast("idleStateHandler", new IdleStateHandler(0, 0, 60));
                pipeline.addLast("handler", handler);
                createConnection(channel);
            }

            private void createConnection(SocketChannel channel) {
                Url url = UrlUtils.getUrlnFromChannel(channel);
                connectionManager.add(new Connection(channel, url), url.getUniqueKey());
            }
        });
    }

    /**
     * 使用默认配置，二期优化
     */
    private void initWriteBufferWaterMark() {

    }

    protected boolean doStart() throws InterruptedException {
        this.channelFuture = this.bootstrap.bind(new InetSocketAddress(ip(), port())).sync();
        return this.channelFuture.isSuccess();
    }

    @Override
    public boolean stop() {
        if (null != this.channelFuture) {
            this.channelFuture.channel().close();
        }
        bossGroup.shutdownGracefully();
        this.connectionManager.removeAll();
        return true;

    }

    @Override
    public String ip() {
        return this.ip;
    }

    @Override
    public int port() {
        return this.port;
    }

    @Override
    public void registerProcessor(byte protocolCode, RpcCommandType commandCode, RemotingProcessor<?> processor) {

    }

    @Override
    public void registerDefaultExecutor(byte protocolCode, ExecutorService executor) {

    }
}
