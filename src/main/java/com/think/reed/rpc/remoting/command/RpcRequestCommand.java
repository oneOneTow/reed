package com.think.reed.rpc.remoting.command;

/**
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/3 13:23
 **/

import lombok.Data;

@Data
public class RpcRequestCommand extends RpcCommand {
    private Object requestHeader;
    private Object requestObject;
    private String requestClass;
    private byte timeout;

    @Override
    public void serialize() {

    }

    @Override
    public void deserialize() {

    }
}
