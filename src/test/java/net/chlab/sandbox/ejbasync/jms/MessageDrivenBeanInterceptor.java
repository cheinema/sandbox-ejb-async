package net.chlab.sandbox.ejbasync.jms;

import java.util.concurrent.CountDownLatch;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.jms.Message;

@Interceptor
public class MessageDrivenBeanInterceptor {

    static CountDownLatch latch = new CountDownLatch(1);
    static String capture;

    @AroundInvoke
    protected Object aroundInvoke(InvocationContext ctx) throws Exception {
        Message message = (Message) ctx.getParameters()[0];
        capture = message.getBody(String.class);

        try {
            return ctx.proceed();
        } finally {
            latch.countDown();
        }
    }
}
