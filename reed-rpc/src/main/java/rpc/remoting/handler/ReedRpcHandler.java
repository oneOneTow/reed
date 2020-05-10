package rpc.remoting.handler;

import rpc.remoting.InvokeContext;
import rpc.remoting.command.CommandHandler;
import rpc.remoting.command.RpcCommandFactory;
import rpc.remoting.command.RpcCommandHandler;
import rpc.remoting.RemotingContext;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/3 10:53
 **/
@ChannelHandler.Sharable
public class ReedRpcHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ReedRpcHandler.class);
    private boolean serverSide;

    public ReedRpcHandler() {
        serverSide = false;
    }

    public ReedRpcHandler(boolean serverSide) {
        this.serverSide = serverSide;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("channelRead:{}", msg);
        CommandHandler commandHandler = new RpcCommandHandler(RpcCommandFactory.getInstance());
        RemotingContext remotingContext = new RemotingContext(ctx, new InvokeContext(), serverSide);
        commandHandler.handleCommand(remotingContext, msg);
    }
}
