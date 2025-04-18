package com.meowu.starter.data.mybatis.plugins.core.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldMapping{

    private String property;
    private String column;
    private String javaType;
    private String typeHandler;
    private Boolean nullable;
}
