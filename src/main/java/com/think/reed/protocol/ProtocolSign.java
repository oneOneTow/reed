package com.think.reed.protocol;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author jgs
 * @date 2020/3/11 16:58
 */
@AllArgsConstructor
public enum ProtocolSign {
  REED((byte) 1);

  @Getter
  private byte type;

  public static ProtocolSign fromValue(byte value) {
    for (ProtocolSign type : ProtocolSign.values()) {
      if (type.getType() == value) {
        return type;
      }
    }
    throw new IllegalArgumentException(String.format("value = %s", value));
  }
}
