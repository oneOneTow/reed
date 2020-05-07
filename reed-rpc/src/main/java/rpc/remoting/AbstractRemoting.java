package rpc.remoting;

import connect.metaobject.Connection;
import rpc.remoting.command.RpcCommand;
import rpc.remoting.command.future.InvokeFuture;

/**
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/2 23:24
 **/
public abstract class AbstractRemoting implements Remoting {

    @Override
    public abstract RpcCommand invokeSync(Connection conn, RpcCommand request, int timeoutMillis);

    @Override
    public abstract void oneWay(Connection conn, RpcCommand request);

    @Override
    public abstract InvokeFuture invokeWithFuture(Connection conn, RpcCommand request, int timeoutMillis);

    @Override
    public abstract void invokeWithCallback(Connection conn, RpcCommand request, InvokeCallback invokeCallback,
                                            int timeoutMillis);
}
