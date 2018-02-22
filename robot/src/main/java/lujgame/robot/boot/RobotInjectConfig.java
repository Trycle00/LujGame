package lujgame.robot.boot;

import lujgame.core.spring.BeanNameGen;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "lujgame",
    nameGenerator = BeanNameGen.class)
public class RobotInjectConfig {

}
