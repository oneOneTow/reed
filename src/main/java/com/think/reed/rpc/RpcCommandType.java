package com.think.reed.rpc;

import lombok.Getter;

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
