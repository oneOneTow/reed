package com.think.reed.rpc.remoting;

import com.think.reed.connect.Connection;
import com.think.reed.rpc.invoke.ReedRpcInvokeContext;
import com.think.reed.rpc.invoke.ReedRpcInvokeFuture;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * base remoteing service
 *
 * @author jgs
 * @date 2020/4/12 16:47
 */
public abstract class ReedRemoting {

  RemotingCommandFactory commandFactory;

  public ReedRemoting(RemotingCommandFactory commandFactory) {
    this.commandFactory = commandFactory;
  }

  abstract ReedRpcInvokeFuture createInvokeFuture(ReedRemotingCommand remotingCommand,
      ReedRpcInvokeContext reedRpcInvokeContext);

  /**
   * @author jgs
   * @date 2020-04-12 17:19
   * @param: conn
   * @param: request
   * @param: timeoutMillis
   * @since 1.0
   */
  protected ReedRpcInvokeFuture invokeWithFuture(final Connection conn,
      final ReedRemotingCommand request,
      final int timeoutMillis) {
    final ReedRpcInvokeFuture future = createInvokeFuture(request,
        request.getReedRpcInvokeContext());
    conn.addInvokeFuture(future);
    final String requestId = request.getId();

    Timeout timeout = TimerHolder.getTimer().newTimeout(new TimerTask() {
      public void run(Timeout timeout) throws Exception {
        future.putResponse(commandFactory.createTimeoutResponse(conn
            .getRemoteAddress()));
      }
    }, timeoutMillis, TimeUnit.MILLISECONDS);

    future.addTimeout(timeout);

    try {
      conn.getChannel().writeAndFlush(request).addListener(new ChannelFutureListener() {
        public void operationComplete(ChannelFuture channelFuture) throws Exception {
          if (!channelFuture.isSuccess()) {
            ReedRpcInvokeFuture reedRpcInvokeFuture = conn.remoteInvokeFuture(requestId);
            if (reedRpcInvokeFuture != null) {
              reedRpcInvokeFuture.cancelTimeout();
              reedRpcInvokeFuture.putResponse(commandFactory.createSendFailedResponse(
                  reedRpcInvokeFuture.getRemoteAddr(), channelFuture.cause()));
            }
          }
        }
      });
    } catch (Exception e) {
      ReedRpcInvokeFuture reedRpcInvokeFuture = conn.remoteInvokeFuture(requestId);
      if (reedRpcInvokeFuture != null) {
        reedRpcInvokeFuture.cancelTimeout();
        reedRpcInvokeFuture
            .putResponse(commandFactory.createSendFailedResponse(conn.getRemoteAddress(), e));
        reedRpcInvokeFuture.tryAsyncExecuteInvokeCallbackAbnormally();
      }
    }
    return future;
  }
}
