package com.think.reed.protocol;

/**
 * @author A
 * @date 2020/3/11 17:15
 */
public class ReedProtocolFactory extends AbstractProtocolFactory {

  Protocol createProtocol(ProtocolSign protocolSign) {
    return new ReedProtocol();
  }
}
