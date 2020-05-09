package connect;

import io.netty.channel.ChannelDuplexHandler;

import javax.sql.ConnectionEventListener;

/**
 * 目前没用
 * 
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/5 01:13
 **/
public class ConnectionEventHandler extends ChannelDuplexHandler {
    private ConnectionManager connectionManager;

    private ConnectionEventListener eventListener;
}
