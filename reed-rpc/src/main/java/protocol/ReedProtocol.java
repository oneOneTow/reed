package protocol;


import codec.ReedProtocolCodeDecoder;
import codec.Decoder;
import codec.Encoder;
import codec.ReedProtocolCodeDecoder;
import codec.ReedProtocolCodeEncoder;

/**
 * @author jgs
 * @date 2020/3/11 16:55
 */
public class ReedProtocol implements Protocol {

  @Override
  public Decoder getDecoder() {
    return new ReedProtocolCodeDecoder();
  }

  @Override
  public Encoder getEncoder() {
    return new ReedProtocolCodeEncoder();
  }
}
