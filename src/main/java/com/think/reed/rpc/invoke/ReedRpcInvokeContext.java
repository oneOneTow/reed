package com.think.reed.rpc.invoke;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jgs
 * @date 2020/4/12 17:10
 */
public class ReedRpcInvokeContext {

  public final static String CLIENT_LOCAL_IP = "reed.client.local.ip";
  public final static String CLIENT_LOCAL_PORT = "reed.client.local.port";
  public final static String CLIENT_REMOTE_IP = "reed.client.remote.ip";
  public final static String CLIENT_REMOTE_PORT = "reed.client.remote.port";
  /**
   * time consumed during connection creating, this is a timespan
   */
  public final static String CLIENT_CONN_CREATETIME = "reed.client.conn.createtime";

  // ~~~ invoke context keys of bolt client and server side
  public final static String REED_INVOKE_REQUEST_ID = "reed.invoke.request.id";
  /**
   * time consumed start from the time when request arrive, to the time when request be processed,
   * this is a timespan
   */
  public final static String REED_PROCESS_WAIT_TIME = "reed.invoke.wait.time";
  public final static String REED_CUSTOM_SERIALIZER = "reed.invoke.custom.serializer";


  public final static int INITIAL_SIZE = 8;

  private ConcurrentHashMap<String, Object> context;

  public ReedRpcInvokeContext() {
    context = new ConcurrentHashMap<String, Object>(8);
  }

}
