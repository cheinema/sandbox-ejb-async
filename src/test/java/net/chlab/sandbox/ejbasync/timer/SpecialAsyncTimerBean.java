package net.chlab.sandbox.ejbasync.timer;

import java.util.concurrent.CountDownLatch;

import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.enterprise.inject.Specializes;

@Specializes
@Stateless
public class SpecialAsyncTimerBean extends AsyncTimerBean {

    static CountDownLatch latch = new CountDownLatch(1);
    static String capture;

    @Override
    @Timeout
    public void onTimeout(Timer timer) {
        capture = (String) timer.getInfo();
        super.onTimeout(timer);
        latch.countDown();
    }
}
