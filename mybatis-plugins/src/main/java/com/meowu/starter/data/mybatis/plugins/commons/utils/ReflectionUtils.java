package com.meowu.starter.data.mybatis.plugins.commons.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Objects;

public class ReflectionUtils{

    private ReflectionUtils(){
        throw new IllegalStateException("Instantiation is not allowed");
    }

    public static <T extends Annotation> T getAnnotation(Class<?> objectType, Class<T> annotationType){
        if(Objects.isNull(objectType)){
            throw new IllegalArgumentException("Object type must not be null");
        }
        if(Objects.isNull(annotationType)){
            throw new IllegalArgumentException("Annotation type must not be null");
        }

        return objectType.getDeclaredAnnotation(annotationType);
    }

    public static <T extends Annotation> T getAnnotation(Field field, Class<T> annotationType){
        if(Objects.isNull(field)){
            throw new IllegalArgumentException("Field must not be null");
        }
        if(Objects.isNull(annotationType)){
            throw new IllegalArgumentException("Annotation type must not be null");
        }

        return field.getDeclaredAnnotation(annotationType);
    }

    public static Field[] getFields(Class<?> objectType){
        if(Objects.isNull(objectType)){
            throw new IllegalArgumentException("Object type must not be null");
        }

        return objectType.getDeclaredFields();
    }
}
