package com.meowu.starter.data.mybatis.plugins.commons.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldInfo{

    private String property;
    private String column;
    private String javaType;
    private String typeHandler;
}
