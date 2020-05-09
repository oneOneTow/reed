package aop.aspect.annotation;

import protocol.ProtocolType;
import java.lang.annotation.*;


@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ReedMapping {
    String clazzName();

    String methodName();

    ProtocolType protocol();
}
