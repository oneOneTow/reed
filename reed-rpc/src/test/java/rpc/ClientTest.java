package rpc;

import connect.ConnectionManager;
import connect.DefaultConnectionManager;
import connect.metaobject.Connection;
import connect.metaobject.Url;
import io.netty.util.Attribute;
import protocol.ProtocolType;
import rpc.remoting.client.ReedClientRemoting;
import rpc.remoting.command.RpcCommand;
import rpc.remoting.command.RpcCommandType;
import rpc.remoting.command.RpcRequestCommand;

/**
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/6 23:15
 **/
public class ClientTest {

    public static void main(String[] args) {

        ConnectionManager connectionManager = DefaultConnectionManager.getInstance();
        Url url = new Url("127.0.0.1", 8081);
        url.setConnectTimeout(5000);
        Connection connection = connectionManager.getAndCreateIfAbsent(url);
        ReedClientRemoting client =  ReedClientRemoting.getInstance();
        Attribute<ProtocolType> s = connection.getChannel().attr(Connection.protocol);
        System.out.println(s);
        RpcCommand request = new RpcRequestCommand();
        request.setId(1);
        request.setType(RpcCommandType.REQUEST);
        request.setClassName("request".getBytes());
        request.setHead("lzq".getBytes());
        client.invokeSync(connection,request,5000);
    }
}
