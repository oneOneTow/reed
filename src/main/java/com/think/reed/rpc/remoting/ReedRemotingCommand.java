package com.think.reed.rpc.remoting;

import com.think.reed.exception.ReedException;
import com.think.reed.protocol.ProtocolType;
import com.think.reed.rpc.RpcCommand;
import com.think.reed.rpc.invoke.ReedRpcInvokeContext;

/**
 * @author jgs
 * @date 2020/4/12 16:58
 */
public interface ReedRemotingCommand {
  String getId();

  ProtocolType getProtocolCode();

  ReedRpcInvokeContext getReedRpcInvokeContext();

  RpcCommand getCmdCode();

  byte getSerializer();

  void serialize() throws ReedException;

  void deserialize() throws ReedException;

  void serializeContent(ReedRpcInvokeContext invokeContext) throws ReedException;

  void deserializeContent(ReedRpcInvokeContext invokeContext) throws ReedException;

}
