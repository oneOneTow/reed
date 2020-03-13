package com.think.reed.protocol;

import com.think.reed.codec.Decoder;
import com.think.reed.codec.Encoder;

/**
 * @author jgs
 * @since 1.0
 */
public interface Protocol {

  Decoder getDecoder();

  Encoder getEncoder();
}
