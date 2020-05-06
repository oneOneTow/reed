package com.think.reed.codec;


import com.think.reed.rpc.remoting.command.RpcCommand;
import com.think.reed.rpc.remoting.command.RpcRequestCommand;
import com.think.reed.rpc.remoting.command.RpcResponseCommand;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author jgs
 * @date 2020/3/13 16:01
 */
public class ReedProtocolCodeEncoder implements Encoder {

  public void encode(ChannelHandlerContext channelHandlerContext, Object msg, ByteBuf out) {
    if (!(msg instanceof RpcCommand)) {
      throw new UnsupportedOperationException("this msg not supported by rpc!");

    }
    RpcCommand cmd = (RpcCommand) msg;
    out.writeByte(cmd.getClassLen());
    out.writeBytes(cmd.getClassName());
    out.writeInt(cmd.getContentLen());
    out.writeBytes(cmd.getHead());
    out.writeByte(cmd.getHeaderLen());

    if (cmd.getClass() == RpcRequestCommand.class) {
      doRequestEncode(out, (RpcRequestCommand) cmd);
    }

    if (cmd.getClass() == RpcResponseCommand.class) {
      doResponseEncode(out, (RpcResponseCommand) cmd);
    }
  }

  private void doRequestEncode(ByteBuf byteBuf, RpcRequestCommand rpcCommand) {
    byte timeout = rpcCommand.getTimeout();
    byteBuf.writeByte(timeout);
  }

  private void doResponseEncode(ByteBuf byteBuf, RpcResponseCommand rpcCommand) {
    byte responseCode = rpcCommand.getResponseCode();
    byteBuf.writeByte(responseCode);
  }
}
