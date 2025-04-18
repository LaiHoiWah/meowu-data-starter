package com.meowu.starter.data.mybatis.plugins.core.criteria.predicate;

import com.meowu.starter.data.mybatis.plugins.core.entity.FieldMapping;
import lombok.Getter;

@Getter
public class Select{

    private final FieldMapping field;

    public Select(FieldMapping field){
        this.field = field;
    }
}
