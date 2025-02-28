package com.meowu.starter.data.mybatis.plugins.criteria.predicate;

import lombok.Getter;

@Getter
public class Predicate<T> extends Expression{

    private T value;
    private Boolean not;

    public Predicate(){
        super();
    }

    public Predicate(String property){
        super(property);
    }

    public void value(T value){
        this.value = value;
    }

    public Predicate<T> not(){
        this.not = true;
        return this;
    }
}
