package com.think.reed.core.aop.aspect.annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ReedRequestParam {
    boolean required() default true;

    String value();
}
