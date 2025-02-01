package com.meowu.starter.data.mybatis.plugins.security.stereotype;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Table{

    String value() default "";
}
