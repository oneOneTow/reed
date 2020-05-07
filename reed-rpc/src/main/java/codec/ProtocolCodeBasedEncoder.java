package codec;

import protocol.Protocol;
import protocol.ProtocolFactory;
import protocol.ProtocolType;
import connect.metaobject.Connection;
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

  @Override
  protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf)
      throws Exception {
    Attribute<ProtocolType> attr = channelHandlerContext.channel().attr(Connection.protocol);
    Protocol protocol = ProtocolFactory.getProtocol(attr.get());
    protocol.getEncoder().encode(channelHandlerContext, o, byteBuf);
  }
}
