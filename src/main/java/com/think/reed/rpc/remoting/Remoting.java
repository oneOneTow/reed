package com.think.reed.rpc.remoting;

import com.think.reed.connect.metaobject.Connection;

/**
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/2 23:11
 **/
public interface Remoting {

    void oneWay(final Connection conn, final RemotingCommand request);

    InvokeFuture invokeWithFuture(final Connection conn, final RemotingCommand request, final int timeoutMillis);

    void invokeWithCallback(final Connection conn,
                            // -
                            final RemotingCommand request,
                            // -
                            final InvokeCallback invokeCallback,
                            // -
                            final int timeoutMillis);
}
