package lujgame.robot.robot.instance.logic;

import akka.actor.ActorRef;
import akka.event.LoggingAdapter;
import com.typesafe.config.Config;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.nio.NioSocketChannel;
import lujgame.robot.netty.RobotNettyInit;
import lujgame.robot.robot.instance.RobotInstanceActor;
import lujgame.robot.robot.instance.RobotInstanceState;
import lujgame.robot.robot.spawn.logic.RobotConfigReader;
import lujgame.robot.robot.spawn.logic.RobotGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RobotConnector {

  @Autowired
  public RobotConnector(RobotConfigReader robotConfigReader,
      RobotBehaver robotBehaver) {
    _robotConfigReader = robotConfigReader;
    _robotBehaver = robotBehaver;
  }

  public void startConnect(RobotInstanceState state, ActorRef instanceRef, LoggingAdapter log) {
    RobotGroup robotGroup = state.getRobotGroup();
    Config robotCfg = robotGroup.getConfig();

    RobotConfigReader r = _robotConfigReader;
    String ip = r.getIp(robotCfg);
    int port = r.getPort(robotCfg);
    log.info("检测目标服务器 -> {}:{}", ip, port);

    new Bootstrap()
        .group(state.getWorkerGroup())
        .channel(NioSocketChannel.class)
//        .option(ChannelOption.SO_KEEPALIVE, true)
        .handler(new RobotNettyInit(instanceRef))
        .connect(ip, port)
//        .addListener(f -> listener.accept(f.isSuccess(), log))
    ;
  }

  public void handleConnectOk(RobotInstanceState state,
      ChannelHandlerContext nettyContext, ActorRef instanceRef, LoggingAdapter log) {
    state.setNettyContext(nettyContext);

    log.debug("连接成功，开始执行行为列表");

    _robotBehaver.initBehave(state.getBehaveState());
    instanceRef.tell(RobotInstanceActor.Behave.MSG, instanceRef);
  }

  public void onConnectDone(boolean success, LoggingAdapter log) {
    log.debug("连接结果：{}", success);
  }

  private final RobotConfigReader _robotConfigReader;
  private final RobotBehaver _robotBehaver;
}
