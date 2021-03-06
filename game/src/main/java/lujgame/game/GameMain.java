package lujgame.game;

import lujgame.game.boot.GameBoot;
import lujgame.game.boot.GameInjectConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class GameMain {

  public static void main(String[] args) {
    new GameMain().start(args);
  }

  private void start(String[] args) {
    try (AnnotationConfigApplicationContext appCtx =
        new AnnotationConfigApplicationContext(GameInjectConfig.class)) {
      GameBoot boot = appCtx.getBean(GameBoot.class);
      boot.boot(args);
    }
  }
}
