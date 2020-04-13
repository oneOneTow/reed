package com.think.reed.rpc.remoting;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 * @author jgs
 * @date 2020/4/12 18:04
 */
public class TimerHolder {

  public static Timer getTimer() {
    return DefaultInstance.INSTANCE;
  }

  public static class DefaultInstance {

    public static final Timer INSTANCE = new HashedWheelTimer(
        new NamedThreadFactory("defult timer", true), 10, TimeUnit.MILLISECONDS);
  }

}
