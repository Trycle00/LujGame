package lujgame.robot.robot.instance;

import akka.actor.ActorRef;
import io.netty.channel.ChannelHandlerContext;
import lujgame.core.akka.CaseActor;
import lujgame.robot.robot.instance.logic.RobotBehaver;
import lujgame.robot.robot.instance.logic.RobotConnector;
import lujgame.robot.robot.instance.message.ConnectOkMsg;

/**
 * 代表一个机器人
 */
public class RobotInstanceActor extends CaseActor {

  public enum Behave {MSG}

  public RobotInstanceActor(
      RobotInstanceState state,
      RobotConnector robotConnector,
      RobotBehaver robotBehaver) {
    _state = state;

    _robotConnector = robotConnector;
    _robotBehaver = robotBehaver;

    registerMessage();
  }

  @Override
  public void preStart() throws Exception {
    ActorRef self = getSelf();
    _robotConnector.startConnect(_state, self, log());
  }

  private void registerMessage() {
    addCase(ConnectOkMsg.class, this::onConnectOk);
//    addCase(ConnectFail.class, this::onConnectFail);

    addCase(Behave.class, this::onBehave);
  }

  private void onConnectOk(ConnectOkMsg msg) {
    _robotConnector.handleConnectOk(_state, msg.getNettyContext(), getSelf(), log());
  }

  //TODO: 连接失败时根据配置是否重连

  private void onBehave(Behave msg) {
    _robotBehaver.doBehave(_state, getSelf(), log());
  }

  private final RobotInstanceState _state;

  private final RobotConnector _robotConnector;
  private final RobotBehaver _robotBehaver;
}
