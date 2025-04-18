package com.meowu.starter.data.mybatis.plugins.core.criteria;

import com.google.common.collect.Lists;
import com.meowu.starter.data.mybatis.plugins.core.criteria.predicate.Expression;
import com.meowu.starter.data.mybatis.plugins.core.criteria.predicate.Select;
import com.meowu.starter.data.mybatis.plugins.core.criteria.predicate.Selects;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
public class SelectQuery{

    private final List<Select> selects = Lists.newArrayList();
    private final List<Expression> expressions = Lists.newArrayList();

    private String tableName;
    private Boolean distinct;

    public void from(String tableName){
        this.tableName = tableName;
    }

    public void selects(Select... selects){
        for(Select select : selects){
            if(Objects.isNull(select)){
                throw new IllegalArgumentException("Selection must not be null");
            }

            this.selects.add(select);
        }
    }

    public void distinct(){
        this.distinct = true;
    }

    public void where(Expression... expressions){
        for(Expression expression : expressions){
            if(Objects.isNull(expression)){
                throw new IllegalArgumentException("Expression must not be null");
            }

            this.expressions.add(expression);
        }
    }
}
