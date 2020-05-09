package codec;

import io.netty.channel.ChannelHandler;

/**
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/5 11:14
 **/
public class ReedCodec implements Codec {

    @Override
    public ChannelHandler newEncoder() {
        return new ProtocolCodeBasedEncoder();
    }

    @Override
    public ChannelHandler newDecoder() {
        return new ProtocolCodeBasedDecoder();
    }
}
