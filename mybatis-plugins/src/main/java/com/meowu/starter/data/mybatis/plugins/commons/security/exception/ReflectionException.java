package com.meowu.starter.data.mybatis.plugins.commons.security.exception;

public class ReflectionException extends RuntimeException{

    public ReflectionException(){
        super();
    }

    public ReflectionException(String message){
        super(message);
    }

    public ReflectionException(Throwable cause){
        super(cause);
    }

    public ReflectionException(String message, Throwable cause){
        super(message, cause);
    }
}
