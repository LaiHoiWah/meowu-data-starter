package com.meowu.starter.data.mybatis.plugins.provider;

import com.meowu.starter.data.mybatis.plugins.commons.entity.MapperInfo;
import com.meowu.starter.data.mybatis.plugins.utils.MapperUtils;
import com.meowu.starter.data.mybatis.plugins.utils.SqlUtils;
import org.apache.ibatis.jdbc.SQL;

public class SqlProvider<T>{

    public String insert(T insert){
        // get mapper info
        MapperInfo mapper = MapperUtils.getMapperInfoWithFieldValue(insert);
        // create sql
        return new SQL(){{
            // insert into
            INSERT_INTO(SqlUtils.getTableName(mapper));
            // into columns
            INTO_COLUMNS(SqlUtils.getNonNullInsertColumns(mapper).toArray(String[]::new));
            // into values
            INTO_VALUES(SqlUtils.getNonNullInsertValues(mapper).toArray(String[]::new));
        }}.toString();
    }

    public String update(T update){
        // get mapper info
        MapperInfo mapper = MapperUtils.getMapperInfoWithFieldValue(update);
        // create sql
        return new SQL(){{
            // update from
            UPDATE(SqlUtils.getTableName(mapper));
            // set update columns
            SET(SqlUtils.getNonNullUpdateSet(mapper).toArray(String[]::new));
            // update by id
            WHERE(SqlUtils.getNonNullWhereConditionByColumn(mapper, "id"));
        }}.toString();
    }

    public String delete(T delete){
        // get mapper info
        MapperInfo mapper = MapperUtils.getMapperInfoWithFieldValue(delete);
        // create sql
        return new SQL(){{
            // delete from
            DELETE_FROM(SqlUtils.getTableName(mapper));
            // update by id
            WHERE(SqlUtils.getNonNullWhereConditionByColumn(mapper, "id"));
        }}.toString();
    }
}
