package com.meowu.starter.data.mybatis.plugins.criteria.predicate;

public class Between extends Predicate<Object[]>{

    public Between(){
        super();
    }

    public Between(String property){
        super(property);
    }

    public void value(Object... value){
        if(value.length != 2){
            throw new IllegalArgumentException("Value item size must be 2");
        }

        super.value(value);
    }
}
