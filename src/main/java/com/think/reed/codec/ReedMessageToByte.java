package com.think.reed.codec;

import com.think.reed.connect.Connection;
import com.think.reed.protocol.ProtocolSign;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.Attribute;

/**
 * 自定义编码
 *
 * @author jgs
 * @date 2020/3/11 16:17
 */
public class ReedMessageToByte extends MessageToByteEncoder<Object> {

  protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf)
      throws Exception {
    Attribute<ProtocolSign> attr = channelHandlerContext.channel().attr(Connection.protocol);
    ProtocolSign protocolSign = attr.get();


  }
}
