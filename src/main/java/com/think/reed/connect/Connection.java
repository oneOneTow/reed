package com.think.reed.connect;

import com.think.reed.protocol.ProtocolSign;
import io.netty.util.AttributeKey;

public interface Connection {
   static final AttributeKey<ProtocolSign> protocol = AttributeKey.newInstance("protocol");

}
