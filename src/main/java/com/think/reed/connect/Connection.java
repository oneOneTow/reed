package com.think.reed.connect;

import com.think.reed.protocol.ProtocolSign;
import com.think.reed.rpc.invoke.ReedRpcInvokeFuture;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Connection {

  static final AttributeKey<ProtocolSign> protocol = AttributeKey.newInstance("protocol");
  private Channel channel;
  private ConcurrentMap<String, ReedRpcInvokeFuture> reedInvokeFutureMap = new ConcurrentHashMap<String, ReedRpcInvokeFuture>();

  public Connection(Channel channel) {
    this.channel = channel;
    this.channel.attr(protocol);
  }


  public Channel getChannel() {
    return channel;
  }

  public ReedRpcInvokeFuture remoteInvokeFuture(String invokeId) {
    return this.reedInvokeFutureMap.remove(invokeId);
  }

  public ReedRpcInvokeFuture getReedInvokeFuture(String invokeId) {
    return this.reedInvokeFutureMap.get(invokeId);
  }

  public ReedRpcInvokeFuture addInvokeFuture(ReedRpcInvokeFuture reedRpcInvokeFuture) {
    return this.reedInvokeFutureMap
        .putIfAbsent(reedRpcInvokeFuture.getInvokeId(), reedRpcInvokeFuture);
  }

  public void close() {
    Iterator<Entry<String, ReedRpcInvokeFuture>> iter = reedInvokeFutureMap.entrySet().iterator();
    while (iter.hasNext()) {
      Entry<String, ReedRpcInvokeFuture> entry = iter.next();
      iter.remove();
      ReedRpcInvokeFuture future = entry.getValue();
      if (future != null) {
        future.putResponse(future.createConnectionClosedResponse(this.getRemoteAddress()));
        future.cancelTimeout();
        future.tryAsyncExecuteInvokeCallbackAbnormally();
      }
    }
  }

  public InetSocketAddress getRemoteAddress() {
    return null;
  }
}
