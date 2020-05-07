package serialzation;


import static io.protostuff.runtime.RuntimeSchema.getSchema;

import exception.ReedException;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

/**
 * reed default serializer use protostuff
 *
 * @author jgs
 * @date 2020/3/15 1:24
 */
public class ReedSerializer implements Serializer {

  public byte[] serialize(Object o) {
    Schema<Object> schema = RuntimeSchema.getSchema(Object.class);
    LinkedBuffer buffer = LinkedBuffer.allocate(512);
    byte[] protostuff = null;
    try {
      protostuff = ProtostuffIOUtil.toByteArray(o, schema, buffer);
    } finally {
      buffer.clear();
      return protostuff;
    }
  }

  public <T> T deserialize(byte[] bytes, String classO) {
    try {
      Class<T> clazz = (Class<T>) Class.forName(classO);
      T returnO = (T) clazz.newInstance();
      Schema<T> schema = getSchema(clazz);
      ProtostuffIOUtil.mergeFrom(bytes, returnO, schema);
      return returnO;
    } catch (Exception e) {
      throw new ReedException(e);
    }
  }
}
