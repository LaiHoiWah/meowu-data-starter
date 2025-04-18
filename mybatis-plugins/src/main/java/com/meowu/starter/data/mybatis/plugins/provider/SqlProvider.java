package com.meowu.starter.data.mybatis.plugins.provider;

import com.meowu.starter.data.mybatis.plugins.commons.entity.Metadata;
import com.meowu.starter.data.mybatis.plugins.criteria.Criteria;
import com.meowu.starter.data.mybatis.plugins.utils.MetadataUtils;
import com.meowu.starter.data.mybatis.plugins.utils.SqlUtils;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public class SqlProvider<T>{

    public String insert(T insert){
        // get Metadata
        Metadata metadata = MetadataUtils.getMetadata(insert);

        // create sql
        return new SQL(){{
            // insert into
            INSERT_INTO(SqlUtils.getTableName(metadata));
            // into columns
            INTO_COLUMNS(SqlUtils.getNonNullInsertColumns(metadata).toArray(String[]::new));
            // into values
            INTO_VALUES(SqlUtils.getNonNullInsertValues(metadata).toArray(String[]::new));
        }}.toString();
    }

    public String update(T update){
        // get Metadata
        Metadata metadata = MetadataUtils.getMetadata(update);
        // create sql
        return new SQL(){{
            // update from
            UPDATE(SqlUtils.getTableName(metadata));
            // set update columns
            SET(SqlUtils.getNonNullUpdateSet(metadata).toArray(String[]::new));
            // update by id
            WHERE(SqlUtils.getNonNullWhereConditionByColumn(metadata, "id"));
        }}.toString();
    }

    public String delete(T delete){
        // get mapper info
        Metadata metadata = MetadataUtils.getMetadata(delete);
        // create sql
        return new SQL(){{
            // delete from
            DELETE_FROM(SqlUtils.getTableName(metadata));
            // update by id
            WHERE(SqlUtils.getNonNullWhereConditionByColumn(metadata, "id"));
        }}.toString();
    }

    public String select(Criteria criteria){
        // get mapper info
//        MapperInfo mapper = MapperUtils.getMapperInfo(criteria.getTypeOf());

        return new SQL(){{

        }}.toString();
    }
}
