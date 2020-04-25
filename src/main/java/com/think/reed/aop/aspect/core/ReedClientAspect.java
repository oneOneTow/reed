package com.think.reed.aop.aspect.core;

import java.lang.reflect.InvocationHandler;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import com.think.reed.aop.aspect.annotation.ReedClient;
import com.think.reed.aop.aspect.annotation.ReedRequestParam;
import com.think.reed.exception.ReedException;
import com.think.reed.rpc.ReedRequest;
import com.think.reed.rpc.ReedResponse;
import com.think.reed.rpc.proxy.ClientProxy;
import com.think.reed.rpc.proxy.Proxy;
import com.think.reed.rpc.proxy.ReedDefaultInvocationHandler;

@Aspect
public class ReedClientAspect {

    @Pointcut("@annotation(com.think.reed.aop.aspect.annotation.ReedRequest)")
    public void doRequest() {}

    @Around("doRequest()")
    public void doRequest(JoinPoint joinPoint) throws NoSuchFieldException {
        if (!this.getClass().isAnnotationPresent(ReedClient.class)) {
            throw new ReedException("reedClient annotation absence!");
        }

        ReedClient reedClient = this.getClass().getAnnotation(ReedClient.class);
        String instance = reedClient.value();

        if (ReedServerCache.reedServerIfExists(instance)) {
            Proxy clientProxy = new ClientProxy();

            InvocationHandler invocationHandler = new ReedDefaultInvocationHandler(clientProxy);
            Object proxyInstance = java.lang.reflect.Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class[] {ClientProxy.class}, invocationHandler);
            ReedServerCache.putIfAbsent(instance, proxyInstance);
        }

        Proxy client = (Proxy)ReedServerCache.getCacheReedServer(instance);

        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Class[] parameterTypes = signature.getParameterTypes();
        String[] parameterNames = signature.getParameterNames();
        Object[] objectValue = joinPoint.getArgs();

        Map<String, Object> props = null;
        if (null != parameterTypes && parameterTypes.length == 0) {

            for (int var = 0; var < parameterTypes.length; var++) {
                Class<?> parameter = parameterTypes[var];
                ReedRequestParam requestParam = null;

                if (!parameter.isAnnotationPresent(ReedRequestParam.class)) {
                    continue;
                }
                if (props == null) {
                    props = new HashMap<String, Object>();
                }
                String value = requestParam.value();

                if ((requestParam = parameter.getAnnotation(ReedRequestParam.class)).required()) {
                    if (null == objectValue[var]) {
                        throw new ReedException("param [ " + value + " ] can't null!");
                    }
                }

                props.put((value == null || value.length() == 0) ? parameterNames[var] : value, objectValue[var]);
            }
        }

        ReedRequest reedRequest = client.buildRequest();

        ReedResponse reedResponse = client.doInvoke(reedRequest);

    }
}
