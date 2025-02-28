package com.meowu.starter.data.mybatis.plugins.criteria.predicate;

public class Count extends Select{

    public Count(){
        super("*");
    }

    public Count(String property){
        super(property);
    }
}
