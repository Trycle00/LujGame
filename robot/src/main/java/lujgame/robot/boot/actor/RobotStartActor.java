package lujgame.robot.boot.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActorContext;
import lujgame.core.akka.common.CaseActor;
import lujgame.robot.robot.spawn.RobotSpawnActorFactory;

/**
 * 机器人启动逻辑
 */
public class RobotStartActor extends CaseActor {

  public RobotStartActor(
      RobotSpawnActorFactory spawnActorFactory) {
    _spawnActorFactory = spawnActorFactory;
  }

  @Override
  public void preStart() throws Exception {
    log().info("机器人程序启动。。");

    loadConfig();
  }

  private void loadConfig() {
    String ip = "127.0.0.1";
    int port = 12345;

    UntypedActorContext ctx = getContext();
    Props spawnProps = _spawnActorFactory.props(ip, port);
    ActorRef spawnActor = ctx.actorOf(spawnProps, "Spawn");

//    ChangeRobotCountMsg msg = new ChangeRobotCountMsg(1);
//    ActorRef self = getSelf();
//    spawnActor.tell(msg, self);
  }

  private final RobotSpawnActorFactory _spawnActorFactory;
}
