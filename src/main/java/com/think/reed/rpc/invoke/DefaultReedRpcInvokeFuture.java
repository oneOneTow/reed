package com.think.reed.rpc.invoke;

import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;

import com.think.reed.protocol.ProtocolType;

import com.think.reed.rpc.remoting.command.RpcCommand;
import com.think.reed.rpc.remoting.command.RpcCommandFactory;
import io.netty.util.Timeout;

/**
 * @author jgs
 * @date 2020/4/12 17:53
 */
public class DefaultReedRpcInvokeFuture implements ReedRpcInvokeFuture {

    private CountDownLatch    countDownLatch = new CountDownLatch(1);
    private String            invokeId;
    private ProtocolType      protocol;
    private RpcCommandFactory commandFactory;

    public DefaultReedRpcInvokeFuture(String invokeId, ProtocolType protocol, RpcCommandFactory commandFactory) {
        this.invokeId = invokeId;
        this.commandFactory = commandFactory;
        this.protocol = protocol;
    }

    public InetSocketAddress getRemoteAddr() {
        return null;
    }

    public String getInvokeId() {
        return null;
    }

    public void addTimeout(Timeout timeout) {

    }

    public void cancelTimeout() {

    }

    public void putResponse(RpcCommand remotingCommand) {

    }

    public void tryAsyncExecuteInvokeCallbackAbnormally() {

    }

    public RpcCommand createConnectionClosedResponse(InetSocketAddress responseHost) {
        return null;
    }
}
