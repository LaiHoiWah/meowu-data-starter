package com.meowu.starter.data.mybatis.plugins.core.criteria.predicate;

import com.meowu.starter.data.mybatis.plugins.core.entity.FieldMapping;

public class Equal extends Predicate{

    public Equal(FieldMapping field, Object value){
        this.field = field;
        this.value = value;
    }
}
