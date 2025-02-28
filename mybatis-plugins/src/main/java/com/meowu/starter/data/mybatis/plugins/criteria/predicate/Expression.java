package com.meowu.starter.data.mybatis.plugins.criteria.predicate;

import lombok.Getter;

@Getter
public class Expression{

    private String property;
    private String alias;

    public Expression(){

    }

    public Expression(String property){
        this.property = property;
    }

    public Expression as(String alias){
        this.alias = alias;
        return this;
    }
}
