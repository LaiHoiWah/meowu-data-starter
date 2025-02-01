package com.meowu.starter.data.mybatis.plugins.criteria;

import com.meowu.starter.data.mybatis.plugins.criteria.predicate.*;

public class Restrictions{

    public Equal equal(String property){
        return new Equal(property);
    }

    public Like like(String property){
        return new Like(property);
    }

    public In in(String property){
        return new In(property);
    }

    public Between between(String property){
        return new Between(property);
    }

    public IsNull isNull(String property){
        return new IsNull(property);
    }
}
