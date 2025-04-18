package com.meowu.starter.data.mybatis.plugins.commons.security.stereotype;

import java.lang.annotation.*;

@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Column{

    String name() default "";

    boolean nullable() default true;

    Class<?> typeHandler();
}
