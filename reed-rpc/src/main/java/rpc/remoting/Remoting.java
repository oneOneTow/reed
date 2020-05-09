package rpc.remoting;

import connect.metaobject.Connection;
import rpc.remoting.command.RpcCommand;
import rpc.remoting.command.future.InvokeFuture;

/**
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/2 23:11
 **/
public interface Remoting {

    /**
     * 同步调用，会阻塞
     * 
     * @param conn
     * @param request
     * @param timeoutMillis
     * @return
     */
    RpcCommand invokeSync(final Connection conn, final RpcCommand request, final int timeoutMillis);

    /**
     * 单次调用
     * 
     * @param conn
     * @param request
     */
    void oneWay(final Connection conn, final RpcCommand request);

    /**
     * 异步调用
     * 
     * @param conn
     * @param request
     * @param timeoutMillis
     * @return
     */
    InvokeFuture invokeWithFuture(final Connection conn, final RpcCommand request, final int timeoutMillis);

    /**
     * 回调函数调用
     * 
     * @param conn
     * @param request
     * @param invokeCallback
     * @param timeoutMillis
     */
    void invokeWithCallback(final Connection conn,
        // -
        final RpcCommand request,
        // -
        final InvokeCallback invokeCallback,
        // -
        final int timeoutMillis);
}
