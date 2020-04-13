package com.think.reed.rpc.invoke;

import com.think.reed.protocol.ProtocolSign;
import com.think.reed.rpc.remoting.ReedRemotingCommand;
import com.think.reed.rpc.remoting.RemotingCommandFactory;
import io.netty.util.Timeout;
import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;

/**
 * @author jgs
 * @date 2020/4/12 17:53
 */
public class DefaultReedRpcInvokeFuture implements ReedRpcInvokeFuture {

  private CountDownLatch countDownLatch = new CountDownLatch(1);
  private String invokeId;
  private ProtocolSign protocol;
  private RemotingCommandFactory commandFactory;

  public DefaultReedRpcInvokeFuture(String invokeId, ProtocolSign protocol,
      RemotingCommandFactory commandFactory) {
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

  public void putResponse(ReedRemotingCommand remotingCommand) {

  }

  public void tryAsyncExecuteInvokeCallbackAbnormally() {

  }

  public ReedRemotingCommand createConnectionClosedResponse(InetSocketAddress responseHost) {
    return null;
  }
}