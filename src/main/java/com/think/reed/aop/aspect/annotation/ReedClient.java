package com.think.reed.aop.aspect.annotation;

import java.lang.annotation.*;

/**
 * reed remote
 *
 * @author jgs
 * @date
 */
@Target({ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ReedClient {
    String instanceName();
}
