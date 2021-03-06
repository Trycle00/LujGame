package lujgame.robot.robot.instance.control.packet;

import static org.assertj.core.api.Assertions.assertThat;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import javax.inject.Inject;
import lujgame.core.net.PacketHeader;
import lujgame.core.net.ReceiveBuffer;
import lujgame.robot.test.RobotTest;
import org.junit.Before;
import org.junit.Test;

public class PacketReceiverTest extends RobotTest {

  @Inject
  PacketReceiver _receiver;

  ByteBuf _netBuf;
  ReceiveBuffer _receiveBuf;

  @Before
  public void setUp() throws Exception {
    _netBuf = Unpooled.buffer();
    _receiveBuf = new ReceiveBuffer();
  }

  @Test
  public void handleImpl_数据不足() throws Exception {
    //-- Arrange --//
    _netBuf.writeShort(1);

    //-- Act --//
    handleImpl();

    //-- Assert --//
    ByteBuf overflowBuf = assertOverflowBuf(2);
    assertThat((int) overflowBuf.readShort()).isEqualTo(1);
//    assertThat(_netBuf.refCnt()).isEqualTo(0);
  }

  @Test
  public void handleImpl_只有头部信息() throws Exception {
    //-- Arrange --//
    _netBuf.writeMedium(111);
    _netBuf.writeMedium(222);
    _netBuf.writeInt(333);

    //-- Act --//
    handleImpl();

    //-- Assert --//
    PacketHeader header = _receiveBuf.getPendingHeader();
    assertThat(header.getOpcode()).isEqualTo(111);
    assertThat(header.getLength()).isEqualTo(222);

    ByteBuf overflowBuf = assertOverflowBuf(4);
    assertThat(overflowBuf.readInt()).isEqualTo(333);
  }

  @Test
  public void handleImpl_完整一个包() throws Exception {
    //-- Arrange --//
    _netBuf.writeMedium(111);
    _netBuf.writeMedium(4);
    _netBuf.writeInt(333);
    _netBuf.writeInt(444);

    //-- Act --//
    handleImpl();

    //-- Assert --//
    assertThat(_receiveBuf.getPendingBody()).isNotNull();

    ByteBuf overflowBuf = assertOverflowBuf(4);
    assertThat(overflowBuf.readInt()).isEqualTo(444);
  }

  void handleImpl() {
    _receiver.receive(_netBuf, _receiveBuf);
  }

  ByteBuf assertOverflowBuf(int size) {
    ByteBuf buf = _receiveBuf.getOverflowBuf();
    assertThat(buf.writerIndex()).isEqualTo(size);
    return buf;
  }
}
