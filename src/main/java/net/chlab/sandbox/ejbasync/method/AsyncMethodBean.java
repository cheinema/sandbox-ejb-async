package net.chlab.sandbox.ejbasync.method;

import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;

@Stateless
public class AsyncMethodBean {

    @Asynchronous
    public Future<String> echo(String text) {
        return new AsyncResult<String>(internalEcho(text));
    }

    private String internalEcho(String text) {
        return "echo " + text;
    }
}
