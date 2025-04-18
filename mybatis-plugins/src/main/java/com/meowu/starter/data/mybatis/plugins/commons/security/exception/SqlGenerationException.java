package com.meowu.starter.data.mybatis.plugins.commons.security.exception;

public class SqlGenerationException extends RuntimeException{

    public SqlGenerationException(){
        super();
    }

    public SqlGenerationException(String message){
        super(message);
    }

    public SqlGenerationException(Throwable cause){
        super(cause);
    }

    public SqlGenerationException(String message, Throwable cause){
        super(message, cause);
    }
}
