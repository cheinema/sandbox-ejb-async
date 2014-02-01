package net.chlab.sandbox.ejbasync.jms;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class AsyncMessagingIT {

    @Resource(mappedName = MessagingConstants.CONNECTION_FACTORY_JNDI)
    ConnectionFactory connectionFactory;

    @Resource(mappedName = MessagingConstants.QUEUE_JNDI)
    Queue queue;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addClasses(AsyncMessagingReceiverBean.class, MessagingConstants.class)
                .addClass(MessageDrivenBeanInterceptor.class)
                .addAsWebInfResource(AsyncMessagingIT.class.getPackage(), "ejb-jar.xml", "ejb-jar.xml");
    }

    @Test
    public void testMessageReceived() throws Exception {
        JMSContext jmsContext = connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE);
        jmsContext.createProducer().send(queue, "test");
        assertTrue("timeout", MessageDrivenBeanInterceptor.latch.await(5L, TimeUnit.SECONDS));
        assertThat(MessageDrivenBeanInterceptor.capture, is("test"));
    }
}
