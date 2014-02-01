package net.chlab.sandbox.ejbasync.method;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.Future;

import javax.inject.Inject;

import net.chlab.sandbox.ejbasync.method.AsyncMethodBean;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class AsyncMethodIT {

    @Inject
    AsyncMethodBean asyncMethodBean;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class).addClass(AsyncMethodBean.class);
    }

    @Test
    public void testEcho() throws Exception {
        Future<String> future = asyncMethodBean.echo("test");
        assertThat(future.get(), is("echo test"));
    }
}
