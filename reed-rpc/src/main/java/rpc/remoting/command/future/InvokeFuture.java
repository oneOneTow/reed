package rpc.remoting.command.future;

import rpc.remoting.command.RpcResponseCommand;

/**
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/2 23:13
 **/
public interface InvokeFuture {

    void putResponse(RpcResponseCommand cmd);

    void cancelTimeout();

}
