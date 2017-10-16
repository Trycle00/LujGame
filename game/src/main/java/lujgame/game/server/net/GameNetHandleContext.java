package lujgame.game.server.net;

import akka.actor.ActorRef;
import akka.event.LoggingAdapter;
import com.google.common.collect.ImmutableList;
import lujgame.game.server.command.CacheOkCommand;
import lujgame.game.server.database.cache.internal.CacheKeyMaker;
import lujgame.game.server.database.cache.message.DbCacheUseItem;
import lujgame.game.server.net.internal.DbCmdInvoker;
import lujgame.game.server.type.JLong;
import lujgame.game.server.type.JStr;
import lujgame.game.server.type.Jstr0;
import org.omg.CORBA.NO_IMPLEMENT;

public class GameNetHandleContext {

  public GameNetHandleContext(
      Object proto,
      ActorRef dbCacheRef,
      ActorRef entityRef,
      LoggingAdapter log,
      Jstr0 strInternal,
      CacheKeyMaker cacheKeyMaker,
      DbCmdInvoker dbCmdInvoker) {
    _proto = proto;

    _dbCacheRef = dbCacheRef;
    _entityRef = entityRef;

    _log = log;

    _strInternal = strInternal;
    _cacheKeyMaker = cacheKeyMaker;

    _dbCmdInvoker = dbCmdInvoker;

    _useList = ImmutableList.builder();
  }

  public <T> T getPacket(GameNetHandler<T> handler) {
    return (T) _proto;
  }

  public long get(JLong val) {
    throw new NO_IMPLEMENT("get尚未实现");
  }

  public String get(JStr val) {
    return _strInternal.getImpl(val).getValue();
  }

  public void dbLoadSet(Class<?> dbType, JStr dbKey, String resultKey) {
    //TODO: 改成带在meta里
    String cacheKey = _cacheKeyMaker.makeSetKey(dbType, dbKey.toString());
    _useList.add(new DbCacheUseItem(cacheKey, dbType, dbKey.toString(), resultKey));
  }

  public <T extends CacheOkCommand> void invoke(Class<T> cmdType) {
    invoke(cmdType, null);
  }

  public <T extends CacheOkCommand> void invoke(Class<T> cmdType, Object packet) {
    _dbCmdInvoker.invoke(cmdType, packet, _useList.build(), _dbCacheRef, _entityRef);
  }

  public LoggingAdapter log() {
    return _log;
  }

  private final Object _proto;

  //  private final Builder<DbCacheUseObjItem> _objUseList;
  private final ImmutableList.Builder<DbCacheUseItem> _useList;

  private final ActorRef _dbCacheRef;
  private final ActorRef _entityRef;

  private final LoggingAdapter _log;

  private final Jstr0 _strInternal;
  private final CacheKeyMaker _cacheKeyMaker;

  private final DbCmdInvoker _dbCmdInvoker;
}
