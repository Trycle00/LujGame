package lujgame.gateway.glue;

import lujgame.core.akka.common.CaseActor;
import lujgame.gateway.network.akka.accept.logic.ForwardBinder;
import lujgame.gateway.network.akka.accept.message.BindForwardReq;

public class GateGlueActor extends CaseActor {

  public GateGlueActor(
      GateGlueActorState state,
      GlueAdminConnector adminConnector,
      ForwardBinder forwardBinder) {
    _state = state;

    _adminConnector = adminConnector;
    _forwardBinder = forwardBinder;

    addCase(BindForwardReq.class, this::onBindForward);
  }

  @Override
  public void preStart() throws Exception {
    _adminConnector.connectAdmin(_state, this);
  }

  private void onBindForward(BindForwardReq msg) {
    _forwardBinder.findForward(_state, msg.getBoxId(), getSender(), getSelf());
  }

  private final GateGlueActorState _state;

  private final GlueAdminConnector _adminConnector;
  private final ForwardBinder _forwardBinder;
}
