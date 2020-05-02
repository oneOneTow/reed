package com.think.reed.rpc.remoting;

import com.think.reed.connect.metaobject.Connection;

/**
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/2 23:24
 **/
public abstract class AbstractRemoting implements Remoting {

    @Override
    public abstract void oneWay(Connection conn, RemotingCommand request);

    @Override
    public abstract InvokeFuture invokeWithFuture(Connection conn, RemotingCommand request, int timeoutMillis);

    @Override
    public abstract void invokeWithCallback(Connection conn, RemotingCommand request, InvokeCallback invokeCallback,
        int timeoutMillis);
}
