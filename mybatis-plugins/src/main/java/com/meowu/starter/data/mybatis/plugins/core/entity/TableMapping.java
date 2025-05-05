package com.meowu.starter.data.mybatis.plugins.core.entity;

import com.google.common.collect.Lists;
import com.meowu.starter.commons.utils.SpellUtils;
import com.meowu.starter.data.mybatis.plugins.commons.security.stereotype.Column;
import com.meowu.starter.data.mybatis.plugins.commons.security.stereotype.Table;
import com.meowu.starter.data.mybatis.plugins.commons.utils.ReflectionUtils;
import lombok.Getter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

@Getter
public class TableMapping{

    private String className;
    private String tableName;
    private List<FieldMapping> fields;

    public TableMapping(){

    }

    public TableMapping(Class<?> objectType){
        init(objectType);
    }

    public void init(Class<?> objectType){
        if(Objects.isNull(objectType)){
            throw new IllegalArgumentException("Object type must not be null");
        }

        // set class name
        className = objectType.getName();

        // set table name
        Table tableAnnotation = ReflectionUtils.getAnnotation(objectType, Table.class);
        tableName = Objects.isNull(tableAnnotation) ? SpellUtils.toUnderScore(objectType.getName()) : tableAnnotation.name();

        // set fields
        Field[] objectFields = ReflectionUtils.getFields(objectType);
        if(ArrayUtils.isNotEmpty(objectFields)){
            fields = Lists.newArrayListWithCapacity(objectFields.length);

            for(Field field : objectFields){
                // convert
                FieldMapping fieldMapping = new FieldMapping();
                // field name
                fieldMapping.setProperty(field.getName());
                fieldMapping.setJavaType(field.getType().getName());

                // get annotation
                Column columnAnnotation = ReflectionUtils.getAnnotation(field, Column.class);
                fieldMapping.setColumn(
                    Objects.isNull(columnAnnotation) || StringUtils.isBlank(columnAnnotation.name()) ?
                        SpellUtils.toUnderScore(field.getName()) : columnAnnotation.name()
                );
                fieldMapping.setTypeHandler(
                    Objects.isNull(columnAnnotation) || Objects.isNull(columnAnnotation.typeHandler()) ?
                        null : columnAnnotation.typeHandler().getName()
                );

                // add into fields
                fields.add(fieldMapping);
            }
        }
    }
}
