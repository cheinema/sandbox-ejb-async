package net.chlab.sandbox.ejbasync.timer;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class AsyncTimerIT {

    @Inject
    AsyncTimerBean asyncTimerBean;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class).addClasses(AsyncTimerBean.class, SpecialAsyncTimerBean.class);
    }

    @Test
    public void testTimer() throws Exception {
        asyncTimerBean.send("test");
        assertTrue("timeout", SpecialAsyncTimerBean.latch.await(1L, TimeUnit.SECONDS));
        assertThat(SpecialAsyncTimerBean.capture, is("test"));
    }
}
