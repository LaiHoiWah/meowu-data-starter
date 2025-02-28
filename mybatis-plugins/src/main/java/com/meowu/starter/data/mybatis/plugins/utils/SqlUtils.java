package com.meowu.starter.data.mybatis.plugins.utils;

import com.meowu.starter.data.mybatis.plugins.commons.entity.FieldData;
import com.meowu.starter.data.mybatis.plugins.commons.entity.FieldInfo;
import com.meowu.starter.data.mybatis.plugins.commons.entity.Metadata;
import com.meowu.starter.data.mybatis.plugins.security.exception.SqlParserException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

public class SqlUtils{

    public static String getTableName(Metadata metadata){
        if(Objects.isNull(metadata)){
            throw new SqlParserException("Metadata must not be null");
        }
        if(Objects.isNull(metadata.getStructure())){
            throw new SqlParserException("Metadata structure must not be null");
        }
        if(StringUtils.isBlank(metadata.getStructure().getTableName())){
            throw new SqlParserException("Metadata structure table name must not be null");
        }

        return metadata.getStructure().getTableName();
    }

    public static List<String> getNonNullInsertColumns(Metadata metadata){
        if(Objects.isNull(metadata)){
            throw new SqlParserException("Metadata must not be null");
        }
        if(CollectionUtils.isEmpty(metadata.getFieldData())){
            throw new SqlParserException("Metadata field data must not be null");
        }

        return metadata.getFieldData()
                       .stream()
                       .filter(FieldData::getHasValue)
                       .map(fieldData -> fieldData.getField().getColumn())
                       .toList();
    }

    public static List<String> getNonNullInsertValues(Metadata metadata){
        if(Objects.isNull(metadata)){
            throw new SqlParserException("Metadata must not be null");
        }
        if(CollectionUtils.isEmpty(metadata.getFieldData())){
            throw new SqlParserException("Metadata field data must not be null");
        }

        return metadata.getFieldData()
                       .stream()
                       .filter(FieldData::getHasValue)
                       .map(fieldData -> {
                            FieldInfo field = fieldData.getField();
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

    public static List<String> getNonNullUpdateSet(Metadata metadata){
        if(Objects.isNull(metadata)){
            throw new SqlParserException("Metadata must not be null");
        }
        if(CollectionUtils.isEmpty(metadata.getFieldData())){
            throw new SqlParserException("Metadata field data must not be null");
        }

        return metadata.getFieldData()
                       .stream()
                       .filter(FieldData::getHasValue)
                       .map(fieldData -> {
                            FieldInfo field = fieldData.getField();
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

    public static String getNonNullWhereConditionByColumn(Metadata metadata, String column){
        if(Objects.isNull(metadata)){
            throw new SqlParserException("Metadata must not be null");
        }
        if(CollectionUtils.isEmpty(metadata.getFieldData())){
            throw new SqlParserException("Metadata field data must not be null");
        }

        return metadata.getFieldData()
                       .stream()
                       .filter(fieldData -> fieldData.getHasValue() && column.equals(fieldData.getField().getColumn()))
                       .findFirst()
                       .map(fieldData -> {
                            FieldInfo field = fieldData.getField();
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
