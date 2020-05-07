package rpc.remoting.server;

import connect.ConnectionManager;
import connect.metaobject.Connection;
import rpc.remoting.AbstractRemoting;
import rpc.remoting.InvokeCallback;
import rpc.remoting.command.future.InvokeFuture;
import rpc.remoting.command.CommandFactory;
import rpc.remoting.command.RpcCommand;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/5 22:08
 **/
public class ReedServerRemoting extends AbstractRemoting {
    private static final Logger logger = LoggerFactory.getLogger(ReedServerRemoting.class);
    protected ConnectionManager connectionManager;
    protected CommandFactory commandFactory;

    public ReedServerRemoting(ConnectionManager connectionManager, CommandFactory commandFactory) {
        this.connectionManager = connectionManager;
        this.commandFactory = commandFactory;
    }

    @Override
    public RpcCommand invokeSync(Connection conn, RpcCommand request, int timeoutMillis) {
        int requestId = request.getId();
        conn.getChannel().writeAndFlush(request).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture f) throws Exception {
                if (!f.isSuccess()) {
                    // TODO:补充失败逻辑
                    logger.error("Invoke send failed, id={}", requestId, f.cause());
                }
            }
        });
        return null;
    }

    @Override
    public void oneWay(Connection conn, RpcCommand request) {

    }

    @Override
    public InvokeFuture invokeWithFuture(Connection conn, RpcCommand request, int timeoutMillis) {
        return null;
    }

    @Override
    public void invokeWithCallback(Connection conn, RpcCommand request, InvokeCallback invokeCallback,
                                   int timeoutMillis) {

    }
}
