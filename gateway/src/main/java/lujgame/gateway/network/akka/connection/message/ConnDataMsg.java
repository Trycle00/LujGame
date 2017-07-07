package lujgame.gateway.network.akka.connection.message;

/**
 * 网络数据从netty投递到akka时所用消息
 */
public class ConnDataMsg {

  public ConnDataMsg(byte[] data) {
    _data = data;
  }

  public byte[] getData() {
    return _data;
  }

  private final byte[] _data;
}
