package protocol;


import codec.Decoder;
import codec.Encoder;

/**
 * @author jgs
 * @since 1.0
 */
public interface Protocol {

  Decoder getDecoder();

  Encoder getEncoder();
}
