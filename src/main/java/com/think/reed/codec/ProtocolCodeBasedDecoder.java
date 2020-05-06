package com.think.reed.codec;

import com.think.reed.connect.metaobject.Connection;
import com.think.reed.protocol.Protocol;
import com.think.reed.protocol.ProtocolFactory;
import com.think.reed.protocol.ProtocolType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.Attribute;
import java.util.List;

/**
 * 自定义编码
 *
 * @author jgs
 * @date 2020/3/11 16:17
 */
public class ProtocolCodeBasedDecoder extends ByteToMessageDecoder {

  protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf,
      List<Object> out) throws Exception {
    Attribute<ProtocolType> attr = channelHandlerContext.channel().attr(Connection.protocol);
    Protocol protocol = ProtocolFactory.getProtocol(attr.get());
    protocol.getDecoder().decode(channelHandlerContext, byteBuf, out);
  }
}
