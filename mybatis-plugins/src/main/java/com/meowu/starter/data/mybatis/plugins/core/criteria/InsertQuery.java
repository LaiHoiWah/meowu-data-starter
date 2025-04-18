package com.meowu.starter.data.mybatis.plugins.core.criteria;

import com.meowu.starter.data.mybatis.plugins.core.criteria.predicate.Predicate;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InsertQuery{

    private String tableName;
    private List<Predicate> predicates;
}
