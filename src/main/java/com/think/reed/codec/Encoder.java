package com.think.reed.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author jgs
 * @date 2020/3/13 15:46
 */
public interface Encoder {

  void encode(ChannelHandlerContext channelHandlerContext,Object o, ByteBuf byteBuf);

}
