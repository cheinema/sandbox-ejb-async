package net.chlab.sandbox.ejbasync.jms;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.jms.Message;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class MessageCollector {

    static final long TIMEOUT_IN_SECONDS = 5L;

    List<Message> messageList = new CopyOnWriteArrayList<>();
    CountDownLatch latch = new CountDownLatch(1);

    public void expectMessages(int numberOfExpectedMessages) {
        messageList.clear();
        latch = new CountDownLatch(numberOfExpectedMessages);
    }

    public void addMessage(Message message) {
        messageList.add(message);
        latch.countDown();
    }

    public void waitForMessages() {
        try {
            if (!latch.await(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)) {
                throw new RuntimeException("Timeout!");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted!", e);
        }
    }

    public List<Message> getAllMessages() {
        return Collections.unmodifiableList(messageList);
    }
}
