package rpc.remoting.client;

import connect.ConnectionManager;
import connect.DefaultConnectionManager;
import connect.metaobject.Connection;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import rpc.remoting.AbstractRemoting;
import rpc.remoting.InvokeCallback;
import rpc.remoting.InvokeContext;
import rpc.remoting.command.CommandFactory;
import rpc.remoting.command.RpcCommandFactory;
import rpc.remoting.command.future.DefaultInvokeFuture;
import rpc.remoting.command.future.InvokeFuture;
import rpc.remoting.command.RpcCommand;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/2 23:24
 **/
public class ReedClientRemoting extends AbstractRemoting {
    private static final Logger logger = LoggerFactory.getLogger(ReedClientRemoting.class);

    protected ConnectionManager connectionManager;
    protected CommandFactory commandFactory;

    private static ReedClientRemoting clientRemoting;

    private ReedClientRemoting() {}


    public static ReedClientRemoting getInstance(){
        if (null == clientRemoting) {
            synchronized (ReedClientRemoting.class){
                if (null == clientRemoting) {
                    clientRemoting = new ReedClientRemoting();
                    clientRemoting.commandFactory = RpcCommandFactory.getInstance();
                    clientRemoting.connectionManager = DefaultConnectionManager.getInstance();
                }
            }
        }
        return clientRemoting;
    }



    @Override
    public RpcCommand invokeSync(Connection conn, RpcCommand request, int timeoutMillis) {
        int requestId = request.getId();
        final InvokeFuture future = createInvokeFuture(request, request.getInvokeContext());
        try {
            conn.getChannel().writeAndFlush(request).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture f) throws Exception {
                    if (!f.isSuccess()) {
                        conn.removeInvokeFuture(requestId);
                        future.putResponse(commandFactory.createSendFailedResponse(conn.getRemoteAddress(), f.cause()));
                        logger.error("Invoke send failed, id={}", requestId, f.cause());
                    }
                }
            });
        } catch (Exception e) {
            conn.removeInvokeFuture(requestId);
            future.putResponse(commandFactory.createSendFailedResponse(conn.getRemoteAddress(), e));
            logger.error("Exception caught when sending invocation,requestId={}", requestId, e);
        }
        RpcCommand response = null;
        try {
            response = future.waitResponse(timeoutMillis);
        } catch (InterruptedException e) {
            conn.removeInvokeFuture(requestId);
            logger.error("wait response exception", e);
        }

        if (null == response) {
            conn.removeInvokeFuture(requestId);
            response = this.commandFactory.createTimeoutResponse(conn.getRemoteAddress());
            logger.error("Wait response, request id={} timeout!", requestId);
        }
        return response;
    }

    private InvokeFuture createInvokeFuture(RpcCommand request, InvokeContext invokeContext) {
        return new DefaultInvokeFuture(request.getId(), null, invokeContext, this.commandFactory);
    }

    @Override
    public void oneWay(Connection conn, RpcCommand request) {

    }

    @Override
    public InvokeFuture invokeWithFuture(Connection conn, RpcCommand request, int timeoutMillis) {
        // TODO: 1.0 not impl
        return null;
    }

    @Override
    public void invokeWithCallback(Connection conn, RpcCommand request, InvokeCallback invokeCallback,
        int timeoutMillis) {
        // TODO: 1.0 not impl
    }


}
