package com.meowu.starter.data.mybatis.plugins.core.criteria.predicate;

import lombok.Getter;

@Getter
public class Predicate extends Expression{

    protected Object value;

    public void value(Object value){
        this.value = value;
    }
}
