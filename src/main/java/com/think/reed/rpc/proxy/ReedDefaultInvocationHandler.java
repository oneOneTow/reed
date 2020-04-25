package com.think.reed.rpc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ReedDefaultInvocationHandler implements InvocationHandler {
    private Proxy proxy;

    public ReedDefaultInvocationHandler(Proxy proxy) {
        this.proxy = proxy;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        return null;
    }
}
