package com.meowu.starter.data.mybatis.plugins.core.criteria.predicate;

import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class Selects{

    private final List<Select> selects = Lists.newArrayList();
    private Boolean distinct;

    public Selects(){

    }

    public Selects distinct(){
        this.distinct = true;
        return this;
    }

    public void select(Select... selects){
        this.selects.addAll(Arrays.stream(selects).toList());
    }
}
