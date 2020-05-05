package com.think.reed.codec;

import com.think.reed.protocol.ProtocolType;

import com.think.reed.rpc.remoting.command.RpcCommand;
import com.think.reed.rpc.remoting.command.RpcCommandType;
import com.think.reed.rpc.remoting.command.RpcRequestCommand;
import com.think.reed.rpc.remoting.command.RpcResponseCommand;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.util.List;

/**
 * @author jgs
 * @date 2020/3/13 16:02
 */
public class ReedProtocolCodeDecoder implements Decoder {

  public void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) {

    RpcCommand rpcCommand = null;

    byte protocolSign = in.readByte();
    byte type = in.readByte();
    byte classLen = in.readByte();
    byte headerLen = in.readByte();
    int contentLen = in.readInt();

    rpcCommand = mapByType(type);

    rpcCommand.setProtocolType(ProtocolType.fromValue(protocolSign));
    rpcCommand.setType(RpcCommandType.fromValue(type));
    rpcCommand.setClassLen(classLen);
    rpcCommand.setHeaderLen(headerLen);

    if (classLen > 0) {
      byte[] clazzName = new byte[classLen];
      in.readBytes(clazzName);
      rpcCommand.setClassName(clazzName);
    }
    if (headerLen > 0) {
      byte[] heads = new byte[headerLen];
      in.readBytes(heads);
      rpcCommand.setHead(heads);
    }
    if (contentLen > 0) {
      byte[] contents = new byte[contentLen];
      in.readBytes(contents);
      rpcCommand.setContent(contents);
    }

    specialDecode(rpcCommand, in);

    out.add(rpcCommand);
  }

  private RpcCommand mapByType(byte type) {
    if (type == RpcCommandType.REQUEST.getType()) {
      return new RpcRequestCommand();
    }

    if (type == RpcCommandType.RESPONSE.getType()) {
      return new RpcResponseCommand();
    }

    if (type == RpcCommandType.HEART_BEAT.getType()) {
      return new RpcRequestCommand();
    }

    throw new RuntimeException("not support protocol type");
  }

  private RpcCommand specialDecode(RpcCommand rpcCommand, ByteBuf byteBuf) {
    if (rpcCommand instanceof RpcRequestCommand) {
      RpcRequestCommand requestCommand = (RpcRequestCommand) rpcCommand;
      byte timeout = byteBuf.readByte();
      requestCommand.setTimeout(timeout);
      return requestCommand;
    }

    if (rpcCommand instanceof RpcResponseCommand) {
      RpcResponseCommand responseCommand = (RpcResponseCommand) rpcCommand;
      byte responseCode = byteBuf.readByte();
      responseCommand.setResponseCode(responseCode);
      return responseCommand;
    }

    throw new RuntimeException("not support rpcCommand");
  }
}
