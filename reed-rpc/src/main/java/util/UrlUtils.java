package util;

import connect.metaobject.Url;
import io.netty.channel.socket.SocketChannel;

import java.net.InetSocketAddress;

/**
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/5 22:23
 **/
public class UrlUtils {

    public static Url getUrlFromChannel(SocketChannel channel) {
        InetSocketAddress address = channel.remoteAddress();
        Url url = new Url(address.getHostString(), address.getPort());
        return url;
    }
}
