package com.think.reed.rpc.remoting.server;

/**
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/6 23:15
 **/
public class ServerTest {

    public static void main(String[] args) {
        ReedRemotingServer server= new ReedRemotingServer("127.0.0.1",8081);
        server.start();
        System.out.println("start");
    }
}
