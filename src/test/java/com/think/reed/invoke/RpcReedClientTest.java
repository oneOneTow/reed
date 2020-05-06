package com.think.reed.invoke;


import org.junit.Test;

public class RpcReedClientTest {

  @Test
  public void invoke_client_send_message() {
    ClientMappingTest mappingTest = new ClientMappingTest();
    mappingTest.save("123");
  }
}
