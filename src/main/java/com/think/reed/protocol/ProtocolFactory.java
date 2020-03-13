package com.think.reed.protocol;

/**
 * @author jgs
 * @date 2020/3/11 17:13
 */
public final class ProtocolFactory {

  public static final Protocol getProtocol(ProtocolSign protocolSign) {
    return ProtocolSign.REED == protocolSign ? new ReedProtocol() : null;
  }
}