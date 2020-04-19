package com.think.reed.aop.aspect.annotation;

import java.lang.annotation.*;

import com.think.reed.protocol.ProtocolType;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ReedRequest {
    String clazzName();

    String methodName();

    ProtocolType protocol();
}
