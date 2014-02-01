package net.chlab.sandbox.ejbasync.jms;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.ejbjar31.EjbJarDescriptor;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class AsyncMessagingIT {

    @Resource(mappedName = MessagingConstants.CONNECTION_FACTORY_JNDI)
    ConnectionFactory connectionFactory;

    @Resource(mappedName = MessagingConstants.QUEUE_JNDI)
    Queue queue;

    @Inject
    MessageCollector messageCollector;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addClasses(AsyncMessagingReceiverBean.class, MessagingConstants.class)
                .addClasses(MessageCollector.class, MessageDrivenBeanInterceptor.class)
                .addAsWebInfResource(new StringAsset(buildEjbJarDescriptor().exportAsString()), "ejb-jar.xml");
    }

    private static EjbJarDescriptor buildEjbJarDescriptor() {
        return Descriptors.create(EjbJarDescriptor.class).getOrCreateAssemblyDescriptor()
                .getOrCreateInterceptorBinding().ejbName(AsyncMessagingReceiverBean.class.getSimpleName())
                .interceptorClass(MessageDrivenBeanInterceptor.class.getName()).getOrCreateMethod()
                .methodName("onMessage").up().up().up();
    }

    @Test
    public void testMessageReceived() throws Exception {
        messageCollector.expectMessages(1);

        JMSContext jmsContext = connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE);
        jmsContext.createProducer().send(queue, "test");

        messageCollector.waitForMessages();
        assertThat(messageCollector.getAllMessages().size(), is(1));
        assertThat(messageCollector.getAllMessages().get(0).getBody(String.class), is("test"));
    }
}
