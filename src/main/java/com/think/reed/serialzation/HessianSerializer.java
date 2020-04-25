package com.think.reed.serialzation;

/**
 *
 * @author jgs
 * @date 2020/3/15 1:19
 */
public class HessianSerializer implements Serializer {

  public byte[] serialize(Object o) {
    return new byte[0];
  }

  public <T> T deserialize(byte[] bytes, String classO) {
    return null;
  }
}
