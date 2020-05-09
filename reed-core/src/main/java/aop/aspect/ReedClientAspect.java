package aop.aspect;

import aop.aspect.annotation.ReedClient;
import aop.aspect.annotation.ReedMapping;
import aop.aspect.annotation.ReedRequestParam;
import exception.ReedException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import medium.ReedRequest;
import medium.ReedResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import proxy.ClientProxy;
import proxy.Proxy;
import proxy.ReedDefaultInvocationHandler;

@Aspect
public class ReedClientAspect {

    @Pointcut("@annotation(aop.aspect.annotation.ReedMapping)")
    public void doRequest() {}

    @Before("doRequest()")
    public void doRequest(JoinPoint joinPoint) {

        Class<?> sourceClass = joinPoint.getTarget().getClass();
        if (!sourceClass.isAnnotationPresent(ReedClient.class)) {
            throw new ReedException("reedClient annotation absence!");
        }

        ReedClient reedClient = sourceClass.getAnnotation(ReedClient.class);
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
        Object[] objectValue = joinPoint.getArgs();
        Method signatureMethod = signature.getMethod();
        ReedMapping reedMapping = signatureMethod.getAnnotation(ReedMapping.class);
        Annotation[][] parameterAnnotations = signatureMethod.getParameterAnnotations();

        Map<String, Object> props = null;

        if (null != parameterAnnotations && parameterAnnotations.length != 0) {
            for(int var = 0; var < parameterAnnotations.length; var++) {
                String paramValue = (String) objectValue[var];
                for(int var2 = 0; var2 < parameterAnnotations[var].length; var2++) {
                    Annotation annotation = parameterAnnotations[var][var2];
                    if(!annotation.annotationType().equals(ReedRequestParam.class)) {
                      continue;
                    }
                    ReedRequestParam reedRequestParam = (ReedRequestParam)annotation;
                    String paramName = reedRequestParam.value();

                    if (reedRequestParam.required()) {
                        if (null == paramName || paramName.length() == 0) {
                            throw new ReedException("request param [ " + paramName + " ] must not be null or blank!");
                        }
                        if(null == props || props.size() == 0) {
                            props = new LinkedHashMap<>();
                            props.put(paramName, paramValue);
                        } else {
                            props.put(paramName, paramValue);
                        }
                    }
                }
            }
        }

        ReedRequest reedRequest = this.buildRequest(instance, reedMapping.clazzName(), reedMapping.methodName(), props);
        ReedResponse reedResponse = client.doInvoke(reedRequest);
    }

    ReedRequest buildRequest(String instanceName,String clazzName, String methodName, Map<String, Object> props) {
        ReedRequest reedRequest = new ReedRequest();
        reedRequest.setInstanceName(instanceName);
        reedRequest.setClazzName(clazzName);
        reedRequest.setMethodName(methodName);
        reedRequest.setRequestProps(props);
        return reedRequest;
    }
}
