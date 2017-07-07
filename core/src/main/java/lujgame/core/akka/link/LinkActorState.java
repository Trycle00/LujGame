package lujgame.core.akka.link;

import akka.actor.ActorRef;

public class LinkActorState {

  public LinkActorState(String linkUrl) {
    _linkUrl = linkUrl;
  }

  public ActorRef getLinkRef() {
    return _linkRef;
  }

  public void setLinkRef(ActorRef linkRef) {
    _linkRef = linkRef;
  }

  public String getLinkUrl() {
    return _linkUrl;
  }

  private ActorRef _linkRef;

  private final String _linkUrl;
}
