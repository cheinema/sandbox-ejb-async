package net.chlab.sandbox.ejbasync.jms;

public interface MessagingConstants {

    static final String CONNECTION_FACTORY_JNDI = "java:/JmsXA";

    static final String QUEUE_NAME = "async";
    static final String QUEUE_JNDI = "java:jboss/jms/queue/" + QUEUE_NAME;
}
