package lujgame.game.server.entity;

import akka.actor.ActorRef;
import com.google.common.collect.ImmutableMap;
import lujgame.game.server.database.bean.DatabaseMeta;
import lujgame.game.server.database.handle.GameDbHandler;
import lujgame.game.server.net.handle.NetHandleSuite;
import lujgame.game.server.net.packet.NetPacketCodec;

public class GameEntityActorState {

  public GameEntityActorState(
      ImmutableMap<Integer, NetHandleSuite> handleSuiteMap,
      ImmutableMap<Class<?>, GameDbHandler> cmdMap,
      ImmutableMap<Class<?>, DatabaseMeta> databaseMetaMap,
      ImmutableMap<Class<?>, NetPacketCodec> netPacketCodecMap,
      ActorRef dbCacheRef,
      ActorRef connRef) {
    _handleSuiteMap = handleSuiteMap;
    _cmdMap = cmdMap;

    _databaseMetaMap = databaseMetaMap;
    _netPacketCodecMap = netPacketCodecMap;

    _dbCacheRef = dbCacheRef;
    _connRef = connRef;
  }

  public ImmutableMap<Integer, NetHandleSuite> getHandleSuiteMap() {
    return _handleSuiteMap;
  }

  public ImmutableMap<Class<?>, GameDbHandler> getCmdMap() {
    return _cmdMap;
  }

  public ImmutableMap<Class<?>, DatabaseMeta> getDatabaseMetaMap() {
    return _databaseMetaMap;
  }

  public ActorRef getDbCacheRef() {
    return _dbCacheRef;
  }

  public ImmutableMap<Class<?>, NetPacketCodec> getNetPacketCodecMap() {
    return _netPacketCodecMap;
  }

  public ActorRef getConnRef() {
    return _connRef;
  }

  private final ImmutableMap<Integer, NetHandleSuite> _handleSuiteMap;
  private final ImmutableMap<Class<?>, GameDbHandler> _cmdMap;

  private final ImmutableMap<Class<?>, DatabaseMeta> _databaseMetaMap;
  private final ImmutableMap<Class<?>, NetPacketCodec> _netPacketCodecMap;

  private final ActorRef _dbCacheRef;
  private final ActorRef _connRef;
}
