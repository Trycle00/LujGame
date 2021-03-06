package lujgame.gateway.network.akka.connection.logic.packet;

public class ConnPacketHeader {

  public static final int HEADER_SIZE = 6;

  public ConnPacketHeader(Integer opcode, int length) {
    _opcode = opcode;
    _length = length;
  }

  public Integer getOpcode() {
    return _opcode;
  }

  public int getLength() {
    return _length;
  }

  private final Integer _opcode;

  /**
   * 包体大小，单位：字节
   */
  private final int _length;
}
