package com.meowu.starter.data.mybatis.plugins.security.stereotype;

import org.apache.ibatis.type.BaseTypeHandler;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Fields{

    String value() default "";

    Class<BaseTypeHandler<?>> typeHandler();
}
