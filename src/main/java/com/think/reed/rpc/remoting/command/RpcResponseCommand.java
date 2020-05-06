package com.think.reed.rpc.remoting.command;

import lombok.Data;

/**
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/3 13:24
 **/
@Data
public class RpcResponseCommand extends RpcCommand {
    private byte responseCode;

    @Override
    public void serialize() {

    }

    @Override
    public void deserialize() {

    }

    public static enum ResponseStatus {
        /**
         * 服务端序列化异常
         */
        SERVER_SERIAL_EXCEPTION,
        /**
         * 线程繁忙
         */
        SERVER_THREADPOOL_BUSY
    }
}
