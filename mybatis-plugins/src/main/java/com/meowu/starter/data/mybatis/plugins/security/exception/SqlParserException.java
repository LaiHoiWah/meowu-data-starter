package com.meowu.starter.data.mybatis.plugins.security.exception;

public class SqlParserException extends RuntimeException{

    public SqlParserException(){
        super();
    }

    public SqlParserException(String message){
        super(message);
    }

    public SqlParserException(Throwable cause){
        super(cause);
    }

    public SqlParserException(String message, Throwable cause){
        super(message, cause);
    }
}
