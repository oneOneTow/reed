package com.think.reed.serialzation;

/**
 * todo 3月16，完成hassian序列化
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
