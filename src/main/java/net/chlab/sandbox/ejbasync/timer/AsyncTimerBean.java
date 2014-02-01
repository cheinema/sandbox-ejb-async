package net.chlab.sandbox.ejbasync.timer;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;

@Stateless
public class AsyncTimerBean {

    @Resource
    TimerService timerService;

    public void send(String text) {
        timerService.createTimer(0L, text);
    }

    @Timeout
    public void onTimeout(Timer timer) {
        System.out.println("timeout with info: " + timer.getInfo());
    }
}
