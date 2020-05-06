package com.think.reed.invoke;

import com.think.reed.core.aop.aspect.annotation.ReedClient;
import com.think.reed.core.aop.aspect.annotation.ReedMapping;
import com.think.reed.core.aop.aspect.annotation.ReedRequestParam;
import com.think.reed.protocol.ProtocolType;

@ReedClient("T1")
public class ClientMappingTest {

  @ReedMapping(clazzName = "Test1", methodName = "save", protocol = ProtocolType.REED)
  public void save(@ReedRequestParam(value = "param1") String param1) {
    System.out.println("t1");
  }
}
