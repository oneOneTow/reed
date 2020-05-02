package com.think.reed.codec;

import com.think.reed.connect.metaobject.Connection;
import com.think.reed.protocol.Protocol;
import com.think.reed.protocol.ProtocolFactory;
import com.think.reed.protocol.ProtocolType;
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
public class ProtocolCodeBasedEncoder extends MessageToByteEncoder<Object> {

  protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf)
      throws Exception {
    Attribute<ProtocolType> attr = channelHandlerContext.channel().attr(Connection.protocol);
    Protocol protocol = ProtocolFactory.getProtocol(attr.get());
    protocol.getEncoder().encode(channelHandlerContext, o, byteBuf);
  }
}
