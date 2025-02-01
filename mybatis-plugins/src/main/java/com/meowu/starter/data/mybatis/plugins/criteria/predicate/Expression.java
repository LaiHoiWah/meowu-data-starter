package com.meowu.starter.data.mybatis.plugins.criteria.predicate;

import lombok.Getter;

@Getter
public class Expression{

    private String property;
    private Boolean not;

    public Expression(){

    }

    public Expression(String property){
        this.property = property;
    }

    public Expression not(){
        this.not = true;
        return this;
    }
}
