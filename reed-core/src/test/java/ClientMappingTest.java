import aop.aspect.annotation.ReedClient;
import aop.aspect.annotation.ReedMapping;
import aop.aspect.annotation.ReedRequestParam;
import protocol.ProtocolType;

@ReedClient("ClientMappingTest")
public class ClientMappingTest {

  @ReedMapping(clazzName = "Test1", methodName = "save", protocol = ProtocolType.REED)
  public void save(@ReedRequestParam(value = "param1") String param1) {
    System.out.println("t1");
  }
}
