package com.think.reed.rpc.invoke;

import java.net.InetSocketAddress;

import com.think.reed.rpc.remoting.ReedRemotingCommand;

import io.netty.util.Timeout;

/**
 * @author jgs
 * @date 2020/4/12 17:10
 */
public interface ReedRpcInvokeFuture {

    InetSocketAddress getRemoteAddr();

    /**
     * get invoke id
     *
     * @author jgs
     * @date 2020-04-12 17:52
     * @param: null
     * @since 1.0
     */
    String getInvokeId();

    /**
     * cancel timeout task
     *
     * @author jgs
     * @date 2020-04-12 17:46
     * @param: null
     * @since 1.0
     */
    void addTimeout(Timeout timeout);

    /**
     * cancel timeout task
     *
     * @author jgs
     * @date 2020-04-12 17:46
     * @param: null
     * @since 1.0
     */
    void cancelTimeout();

    /**
     * @author jgs
     * @date 2020-04-12 17:52
     * @param: null
     * @since 1.0
     */
    void putResponse(ReedRemotingCommand remotingCommand);

    /**
     * async execute callback
     *
     * @author jgs
     * @date 2020-04-12 17:58
     * @param: null
     * @since 1.0
     */
    void tryAsyncExecuteInvokeCallbackAbnormally();

    /**
     * @author jgs
     * @date 2020-04-12 18:00
     * @param: responseHost
     * @since 1.0
     */
    ReedRemotingCommand createConnectionClosedResponse(InetSocketAddress responseHost);

}
