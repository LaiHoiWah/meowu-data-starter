package com.meowu.starter.data.mybatis.plugins.commons.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class FieldData{

    private FieldInfo field;
    private Object value;

    public Boolean getHasValue(){
        return Objects.isNull(this.value);
    }
}
