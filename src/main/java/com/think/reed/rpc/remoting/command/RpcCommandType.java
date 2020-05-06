package com.think.reed.rpc.remoting.command;

import lombok.Getter;

/**
 * @author zhiqing.lu
 * @version v1.0.0
 * @date 2020/5/3 11:04
 **/
public enum RpcCommandType {
    REQUEST((byte) 1),
    RESPONSE((byte) 1),
    HEART_BEAT((byte) 1);

    @Getter
    private byte type;

    RpcCommandType(byte b) {
        this.type = b;
    }

    public static RpcCommandType fromValue(byte value) {
        for (RpcCommandType type : RpcCommandType.values()) {
            if (type.getType() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException(String.format("value = %s", value));
    }

}
