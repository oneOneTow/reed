package util;

import io.netty.channel.Channel;
import org.apache.commons.lang.StringUtils;

import java.net.SocketAddress;

/**
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/2 21:49
 **/
public class RemoteUtils {
    /**
     * /127.0.0.1:1234 -> 127.0.0.1
     * 
     */
    public static String parseRemoteAddress(final Channel channel) {
        if (null == channel) {
            return "";
        }
        final SocketAddress remote = channel.remoteAddress();
        return doParse(remote != null ? remote.toString().trim() : "");
    }

    private static String doParse(String addr) {
        if (org.apache.commons.lang.StringUtils.isBlank(addr)) {
            return StringUtils.EMPTY;
        }
        if (addr.charAt(0) == '/') {
            return addr.substring(1);
        } else {
            int len = addr.length();
            for (int i = 1; i < len; ++i) {
                if (addr.charAt(i) == '/') {
                    return addr.substring(i + 1);
                }
            }
            return addr;
        }
    }
}
