package com.meowu.starter.data.mybatis.plugins.commons.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.meowu.starter.data.mybatis.plugins.commons.security.exception.ReflectionException;
import com.meowu.starter.data.mybatis.plugins.commons.security.exception.SqlGenerationException;
import com.meowu.starter.data.mybatis.plugins.core.criteria.SelectQuery;
import com.meowu.starter.data.mybatis.plugins.core.criteria.predicate.Select;
import com.meowu.starter.data.mybatis.plugins.core.entity.FieldMapping;
import com.meowu.starter.data.mybatis.plugins.core.entity.TableMapping;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SqlUtils{

    private final static Map<String, TableMapping> MAPPING_CACHE = Maps.newConcurrentMap();

    private SqlUtils(){
        throw new IllegalStateException("Instantiation is not allowed");
    }

    public static String getInsertSql(Object object){
        if(Objects.isNull(object)){
            throw new SqlGenerationException("Object must not be null");
        }

        // get object type
        Class<?> typeOf = object.getClass();
        // get table mapping
        TableMapping mapping = MAPPING_CACHE.computeIfAbsent(typeOf.getName(), k -> new TableMapping(typeOf));
        // insert columns
        List<FieldMapping> insertColumns = Lists.newArrayList();
        // object fields
        Field[] objectFields = ReflectionUtils.getFields(typeOf);
        for(Field field : objectFields){
            try{
                // get value
                field.setAccessible(true);
                Object value = field.get(object);

                // if value is not null, put into column list
                if(Objects.nonNull(value)){
                    insertColumns.add(mapping.getFields().stream().filter(f -> f.getProperty().equals(field.getName())).findFirst().orElseThrow());
                }
            }catch(Exception e){
                throw new ReflectionException(e.getMessage(), e);
            }
        }

        if(CollectionUtils.isEmpty(insertColumns)){
            throw new SqlGenerationException("No column has been inserted");
        }

        return new SQL(){{
            // into table
            INSERT_INTO(mapping.getTableName());
            // set column and value
            for(FieldMapping field : insertColumns){
                VALUES(field.getColumn(), getInsertValueSql(field));
            }
        }}.toString();
    }

    public static String getSelectSql(SelectQuery selectQuery){
        if(Objects.isNull(selectQuery)){
            throw new IllegalArgumentException("Select condition must not be null");
        }

        // get select setting
        boolean distinct = Objects.nonNull(selectQuery.getDistinct()) && selectQuery.getDistinct();
        // select columns
        List<String> columns = selectQuery.getSelects()
                                          .stream()
                                          .map(Select::getField)
                                          .map(FieldMapping::getColumn)
                                          .toList();

        if(CollectionUtils.isEmpty(columns)){
            columns.add("*");
        }

        return new SQL(){{
            // select
            if(distinct){
                SELECT_DISTINCT(columns.toArray(String[]::new));
            }else{
                SELECT(columns.toArray(String[]::new));
            }

            // from
            FROM(selectQuery.getTableName());

            // where

        }}.toString();
    }

    private static String getInsertValueSql(FieldMapping field){
        if(Objects.isNull(field)){
            throw new SqlGenerationException("Field mapping must not be null");
        }

        StringBuilder builder = new StringBuilder();
        builder.append("#{").append(field.getProperty());
        // add java type
        if(StringUtils.isNotBlank(field.getJavaType())){
            builder.append(", javaType=").append(field.getJavaType());
        }
        // add type handler
        if(StringUtils.isNotBlank(field.getTypeHandler())){
            builder.append(", typeHandler=").append(field.getTypeHandler());
        }
        builder.append("}");
        return builder.toString();
    }
}
