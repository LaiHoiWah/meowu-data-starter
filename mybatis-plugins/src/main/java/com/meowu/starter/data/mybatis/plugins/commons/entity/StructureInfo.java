package com.meowu.starter.data.mybatis.plugins.commons.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StructureInfo{

    private String tableName;
    private List<FieldInfo> fields;
}
