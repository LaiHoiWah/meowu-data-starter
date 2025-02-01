package com.meowu.starter.data.mybatis.plugins.criteria.predicate;

public class Like extends Predicate<String>{

    public Like(){
        super();
    }

    public Like(String property){
        super(property);
    }

    public String getValue(){
        return "%" + super.getValue() + "%";
    }
}
