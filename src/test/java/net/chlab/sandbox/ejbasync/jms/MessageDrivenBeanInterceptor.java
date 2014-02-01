package net.chlab.sandbox.ejbasync.jms;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.jms.Message;

@Interceptor
public class MessageDrivenBeanInterceptor {

    @Inject
    MessageCollector messageCollector;

    @AroundInvoke
    protected Object aroundInvoke(InvocationContext ctx) throws Exception {
        Message message = (Message) ctx.getParameters()[0];

        try {
            return ctx.proceed();
        } finally {
            messageCollector.addMessage(message);
        }
    }
}
