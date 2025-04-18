package com.meowu.starter.data.mybatis.plugins.core.criteria.predicate;

import com.meowu.starter.data.mybatis.plugins.core.entity.FieldMapping;
import lombok.Getter;

@Getter
public class Expression{

    protected FieldMapping field;
    protected Boolean not;

    public Expression not(){
        this.not = true;
        return this;
    }
}
