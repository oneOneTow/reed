package rpc.remoting;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/3 11:07
 **/
public class InvokeContext {

    // ~~~ invoke context keys of client side
    public final static String CLIENT_LOCAL_IP = "reed.client.local.ip";
    public final static String CLIENT_LOCAL_PORT = "reed.client.local.port";
    public final static String CLIENT_REMOTE_IP = "reed.client.remote.ip";
    public final static String CLIENT_REMOTE_PORT = "reed.client.remote.port";
    /**
     * time consumed during connection creating, this is a timespan
     */
    public final static String CLIENT_CONN_CREATETIME = "reed.client.conn.createtime";

    // ~~~ invoke context keys of server side
    public final static String SERVER_LOCAL_IP = "reed.server.local.ip";
    public final static String SERVER_LOCAL_PORT = "reed.server.local.port";
    public final static String SERVER_REMOTE_IP = "reed.server.remote.ip";
    public final static String SERVER_REMOTE_PORT = "reed.server.remote.port";

    // ~~~ invoke context keys of reed client and server side
    public final static String REED_INVOKE_REQUEST_ID = "reed.invoke.request.id";
    /**
     * time consumed start from the time when request arrive, to the time when request be processed, this is a timespan
     */
    public final static String REED_PROCESS_WAIT_TIME = "reed.invoke.wait.time";
    public final static String REED_CUSTOM_SERIALIZER = "reed.invoke.custom.serializer";
    public final static String REED_CRC_SWITCH = "reed.invoke.crc.switch";

    private ConcurrentHashMap<String, Object> context;

    public void putIfAbsent(String key, Object value) {
        this.context.putIfAbsent(key, value);
    }
}
