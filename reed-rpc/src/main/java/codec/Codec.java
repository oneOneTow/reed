package codec;

import io.netty.channel.ChannelHandler;

/**
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/5 11:13
 **/
public interface Codec {
    /**
     * Create an encoder instance.
     *
     * @return new encoder instance
     */
    ChannelHandler newEncoder();

    /**
     * Create an decoder instance.
     *
     * @return new decoder instance
     */
    ChannelHandler newDecoder();
}
