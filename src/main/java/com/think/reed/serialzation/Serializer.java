package com.think.reed.serialzation;

/**
 * @author jgs
 * @date 2020/3/15 0:41
 */
public interface Serializer {

  public byte[] serialize(final Object o);

  public <T> T deserialize(byte[] bytes,String classO);
}
