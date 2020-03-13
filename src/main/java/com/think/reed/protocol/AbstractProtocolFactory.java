package com.think.reed.protocol;

/**
 * @author A
 * @date 2020/3/11 17:13
 */
public abstract class AbstractProtocolFactory {
    abstract Protocol createProtocol(ProtocolSign protocolSign);
}
