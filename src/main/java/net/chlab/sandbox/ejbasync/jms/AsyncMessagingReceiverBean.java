package net.chlab.sandbox.ejbasync.jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSDestinationDefinition;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(activationConfig = { @ActivationConfigProperty(propertyName = "destinationLookup",
        propertyValue = MessagingConstants.QUEUE_JNDI) })
@JMSDestinationDefinition(name = MessagingConstants.QUEUE_JNDI,
        destinationName = MessagingConstants.QUEUE_NAME,
        description = MessagingConstants.QUEUE_NAME,
        interfaceName = "javax.jms.Queue")
public class AsyncMessagingReceiverBean implements MessageListener {

    @Override
    public void onMessage(Message message) {
    }
}
