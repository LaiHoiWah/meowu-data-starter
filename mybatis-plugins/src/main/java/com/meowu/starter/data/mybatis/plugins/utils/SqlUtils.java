package com.meowu.starter.data.mybatis.plugins.utils;

import com.meowu.starter.data.mybatis.plugins.commons.entity.FieldInfo;
import com.meowu.starter.data.mybatis.plugins.commons.entity.MapperInfo;
import com.meowu.starter.data.mybatis.plugins.security.exception.SqlParserException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

public class SqlUtils{

    public static String getTableName(MapperInfo mapper){
        if(Objects.isNull(mapper)){
            throw new SqlParserException("Mapper info must not be null");
        }

        if(StringUtils.isBlank(mapper.getTableName())){
            throw new SqlParserException("Table name must not be null");
        }

        return mapper.getTableName();
    }

    public static List<String> getNonNullInsertColumns(MapperInfo mapper){
        if(Objects.isNull(mapper)){
            throw new SqlParserException("Mapper info must not be null");
        }

        if(CollectionUtils.isEmpty(mapper.getFields())){
            throw new SqlParserException("Field info must not be null");
        }

        return mapper.getFields()
                     .stream()
                     .filter(field -> Objects.nonNull(field.getValue()))
                     .map(FieldInfo::getColumn)
                     .toList();
    }

    public static List<String> getNonNullInsertValues(MapperInfo mapper){
        if(Objects.isNull(mapper)){
            throw new SqlParserException("Mapper info must not be null");
        }

        if(CollectionUtils.isEmpty(mapper.getFields())){
            throw new SqlParserException("Field info must not be null");
        }

        return mapper.getFields()
                     .stream()
                     .filter(field -> Objects.nonNull(field.getValue()))
                     .map(field -> {
                            StringBuilder sql = new StringBuilder();
                            sql.append("#{").append(field.getProperty());
                            if(StringUtils.isNotBlank(field.getJavaType())){
                                sql.append(", javaType=").append(field.getJavaType());
                            }
                            if(StringUtils.isNotBlank(field.getTypeHandler())){
                                sql.append(", typeHandler=").append(field.getTypeHandler());
                            }
                            sql.append("}");
                            return sql.toString();
                     })
                     .toList();
    }

    public static List<String> getNonNullUpdateSet(MapperInfo mapper){
        if(Objects.isNull(mapper)){
            throw new SqlParserException("Mapper info must not be null");
        }

        if(CollectionUtils.isEmpty(mapper.getFields())){
            throw new SqlParserException("Field info must not be null");
        }

        return mapper.getFields()
                     .stream()
                     .filter(field -> Objects.nonNull(field.getValue()))
                     .map(field -> {
                            StringBuilder sql = new StringBuilder();
                            sql.append(field.getColumn()).append(" = #{").append(field.getProperty());
                            if(StringUtils.isNotBlank(field.getJavaType())){
                                sql.append(", javaType=").append(field.getJavaType());
                            }
                            if(StringUtils.isNotBlank(field.getTypeHandler())){
                                sql.append(", typeHandler=").append(field.getTypeHandler());
                            }
                            sql.append("}");
                            return sql.toString();
                     })
                     .toList();
    }

    public static String getNonNullWhereConditionByColumn(MapperInfo mapper, String column){
        if(Objects.isNull(mapper)){
            throw new SqlParserException("Mapper info must not be null");
        }

        if(StringUtils.isBlank(column)){
            throw new SqlParserException("Column must not be null");
        }

        if(CollectionUtils.isEmpty(mapper.getFields())){
            throw new SqlParserException("Field info must not be null");
        }

        return mapper.getFields()
                     .stream()
                     .filter(field -> Objects.nonNull(field.getValue()) && column.equals(field.getColumn()))
                     .findFirst()
                     .map(field -> {
                            StringBuilder sql = new StringBuilder();
                            sql.append(field.getColumn()).append(" = #{").append(field.getProperty());
                            if(StringUtils.isNotBlank(field.getJavaType())){
                                sql.append(", javaType=").append(field.getJavaType());
                            }
                            if(StringUtils.isNotBlank(field.getTypeHandler())){
                                sql.append(", typeHandler=").append(field.getTypeHandler());
                            }
                            sql.append("}");
                            return sql.toString();
                     })
                     .orElseThrow(() -> new SqlParserException("Column is not exists"));
    }
}
