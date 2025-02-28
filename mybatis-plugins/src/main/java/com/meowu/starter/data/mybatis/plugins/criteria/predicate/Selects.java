package com.meowu.starter.data.mybatis.plugins.criteria.predicate;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Selects{

    private List<? extends Select> selects;
    private Boolean distinct;

    public Selects(){
        this.selects = Lists.newArrayList();
        this.distinct = false;
    }
}
