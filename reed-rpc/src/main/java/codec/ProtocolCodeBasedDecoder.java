package codec;

import protocol.Protocol;
import protocol.ProtocolFactory;
import protocol.ProtocolType;
import connect.metaobject.Connection;
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

  @Override
  protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf,
      List<Object> out) throws Exception {
    Attribute<ProtocolType> attr = channelHandlerContext.channel().attr(Connection.protocol);
    Protocol protocol = ProtocolFactory.getProtocol(attr.get());
    protocol.getDecoder().decode(channelHandlerContext, byteBuf, out);
  }
}
