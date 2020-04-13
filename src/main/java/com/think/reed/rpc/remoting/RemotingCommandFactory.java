package com.think.reed.rpc.remoting;

import com.think.reed.rpc.RpcCommand;
import java.net.InetSocketAddress;

public interface RemotingCommandFactory {

  <T extends ReedRemotingCommand> T createRequestCommand(final Object requestObject);

  <T extends ReedRemotingCommand> T createResponse(final Object responseObject,
      RpcCommand requestCmd);

  <T extends ReedRemotingCommand> T createTimeoutResponse(
      final InetSocketAddress inetSocketAddress);

  <T extends ReedRemotingCommand> T createSendFailedResponse(
      final InetSocketAddress inetSocketAddress, Throwable throwable);
}