package codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.util.List;

/**
 * @author jgs
 * @date 2020/3/13 15:46
 */
public interface Decoder {

  void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> o);

}
