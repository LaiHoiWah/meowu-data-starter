package com.meowu.starter.data.mybatis.plugins.criteria;

import com.google.common.collect.Lists;
import com.meowu.starter.data.mybatis.plugins.criteria.predicate.Expression;
import com.meowu.starter.data.mybatis.plugins.criteria.predicate.Select;
import com.meowu.starter.data.mybatis.plugins.criteria.predicate.Selects;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class Criteria{

    private final Class<?> typeOf;
    private final Selects selects;
    private final List<Expression> conditions;

    public Criteria(Class<?> typeOf){
        this.typeOf = typeOf;
        this.selects = new Selects();
        this.conditions = Lists.newArrayList();
    }

    public void select(Select... selects){
        this.selects.getSelects().addAll(Arrays.stream(selects).toList());
    }

    public void where(Expression... expressions){
        this.conditions.addAll(Arrays.stream(expressions).toList());
    }
}
