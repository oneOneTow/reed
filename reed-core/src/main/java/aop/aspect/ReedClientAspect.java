package aop.aspect;

import aop.aspect.annotation.ReedClient;
import aop.aspect.annotation.ReedMapping;
import aop.aspect.annotation.ReedRequestParam;
import exception.ReedException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import medium.ReedRequest;
import medium.ReedResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
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
        Class[] parameterTypes = signature.getParameterTypes();
        String[] parameterNames = signature.getParameterNames();
        Object[] objectValue = joinPoint.getArgs();
        Method signatureMethod = signature.getMethod();
        ReedMapping reedMapping = signatureMethod.getAnnotation(ReedMapping.class);

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
