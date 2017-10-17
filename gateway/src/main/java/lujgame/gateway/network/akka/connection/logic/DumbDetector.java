package lujgame.gateway.network.akka.connection.logic;

import akka.actor.ActorRef;
import akka.event.LoggingAdapter;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;
import lujgame.core.akka.AkkaTool;
import lujgame.core.akka.common.CaseActor;
import lujgame.gateway.network.akka.connection.GateConnActor;
import lujgame.gateway.network.akka.connection.logic.state.ConnActorState;
import lujgame.gateway.network.akka.connection.message.ConnRecvMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DumbDetector {

  @Autowired
  public DumbDetector(AkkaTool akkaTool,
      ConnInfoGetter connInfoGetter) {
    _akkaTool = akkaTool;
    _connInfoGetter = connInfoGetter;
  }

  public void startDetect(CaseActor actor) {
    _akkaTool.schedule(actor, 3, TimeUnit.SECONDS,
        ScheduleId.DUMB, GateConnActor.Dumb.MSG, ConnRecvMsg.class);
  }

  public void destroyDumb(ConnActorState state, ActorRef connRef, LoggingAdapter log) {
    InetSocketAddress remoteAddr = _connInfoGetter.getRemoteAddress(state);
    log.warning("[非法]检测到空连接，即将销毁 -> {}", remoteAddr);

    connRef.tell(GateConnActor.Destroy.MSG, connRef);
  }

  private interface ScheduleId {

    String DUMB = GateConnActor.Dumb.class.getName();
  }

  private final AkkaTool _akkaTool;
  private final ConnInfoGetter _connInfoGetter;
}
