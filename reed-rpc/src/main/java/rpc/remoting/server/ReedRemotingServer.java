package rpc.remoting.server;

import codec.Codec;
import codec.ReedCodec;
import connect.ConnectionManager;
import connect.DefaultConnectionManager;
import connect.factory.DefaultConnectionFactory;
import connect.metaobject.Connection;
import connect.metaobject.Url;
import rpc.remoting.Remoting;
import rpc.remoting.command.RpcCommandType;
import rpc.remoting.command.RpcCommandFactory;
import rpc.remoting.handler.ReedRpcHandler;
import rpc.remoting.process.RemotingProcessor;
import util.UrlUtils;
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

    private static ReedRemotingServer reedRemotingServer;
    private AtomicBoolean started = new AtomicBoolean(false);
    private String ip;
    private int port;
    protected Remoting rpcRemoting;

    private Codec codec = new ReedCodec();
    private ConnectionManager connectionManager;
    private ServerBootstrap bootstrap;
    private ChannelFuture channelFuture;
    private static final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    /** worker event loop group. Reuse I/O worker threads between rpc servers. */
    private static final EventLoopGroup workerGroup = new NioEventLoopGroup(4);

    private ReedRemotingServer() {

    }

    public static ReedRemotingServer getInstance() {
        if (null == reedRemotingServer) {
            synchronized (ReedRemotingServer.class) {
                if (null == reedRemotingServer) {
                    reedRemotingServer = new ReedRemotingServer();
                }
            }
        }
        return reedRemotingServer;
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
        this.connectionManager = DefaultConnectionManager.getInstance();
        this.rpcRemoting = new ReedServerRemoting(this.connectionManager,RpcCommandFactory.getInstance());
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

                Url url = UrlUtils.getUrlFromChannel(channel);
                connectionManager.add(new Connection(channel, url), url.getUniqueKey());
                channel.pipeline().fireUserEventTriggered(DefaultConnectionFactory.ConnectionEventType.CONNECT);
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

    public void setIp(String ip) {
        this.ip = ip;
    }


    @Override
    public int port() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public void registerProcessor(byte protocolCode, RpcCommandType commandCode, RemotingProcessor<?> processor) {

    }

    @Override
    public void registerDefaultExecutor(byte protocolCode, ExecutorService executor) {

    }
}
