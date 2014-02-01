package net.chlab.sandbox.ejbasync.jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(activationConfig = { @ActivationConfigProperty(propertyName = "destinationLookup",
        propertyValue = JndiConstants.JNDI_QUEUE) })
public class AsyncMessagingReceiverBean implements MessageListener {

    @Override
    public void onMessage(Message message) {
    }
}
