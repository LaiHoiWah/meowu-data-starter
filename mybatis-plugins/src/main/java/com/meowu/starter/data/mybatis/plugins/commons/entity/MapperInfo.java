package com.meowu.starter.data.mybatis.plugins.commons.entity;

import com.meowu.starter.commons.utils.GsonUtils;
import com.meowu.starter.data.mybatis.plugins.utils.MapperUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MapperInfo implements Cloneable{

    private Object mapper;
    private String tableName;
    private List<FieldInfo> fields;

    @Override
    protected MapperInfo clone() throws CloneNotSupportedException{
        // using Gson to do deep clone
        String json = GsonUtils.toJson(this);
        return GsonUtils.fromJson(json, this.getClass());
    }
}
