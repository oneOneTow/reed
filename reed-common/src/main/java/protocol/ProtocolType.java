package protocol;


import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
public enum ProtocolType {
  REED((byte) 1);

  @Getter
  private byte type;

  public static ProtocolType fromValue(byte value) {
    for (ProtocolType type : ProtocolType.values()) {
      if (type.getType() == value) {
        return type;
      }
    }
    throw new IllegalArgumentException(String.format("value = %s", value));
  }
}
