package com.think.reed.protocol;

import com.think.reed.codec.Decoder;
import com.think.reed.codec.Encoder;
import com.think.reed.codec.ReedProtocolCodeDecoder;
import com.think.reed.codec.ReedProtocolCodeEncoder;

/**
 * @author jgs
 * @date 2020/3/11 16:55
 */
public class ReedProtocol implements Protocol {

  public Decoder getDecoder() {
    return new ReedProtocolCodeDecoder();
  }

  public Encoder getEncoder() {
    return new ReedProtocolCodeEncoder();
  }
}
