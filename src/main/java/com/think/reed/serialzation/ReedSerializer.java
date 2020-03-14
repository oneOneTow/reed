package com.think.reed.serialzation;

/**
 * @author A
 * @date 2020/3/15 1:24
 */
public class ReedSerializer implements Serializer {

  public byte[] serialize(Object o) {
    return new byte[0];
  }

  public <T> T deserialize(byte[] bytes, String classO) {
    return null;
  }
}
