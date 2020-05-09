package serialzation;

/**
 * @author jgs
 * @date 2020/3/15 1:25
 */
public class SerializerFactory {

  /**
   * 默认走hassian
   *
   * @author jgs
   * @date 2020-03-15 01:26
   * @param: null
   * @since 1.0
   */
  Serializer createSerializer() {
    return new HessianSerializer();
  }
}
