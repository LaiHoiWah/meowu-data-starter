package com.meowu.starter.data.mybatis.plugins.core.provider;

import com.meowu.starter.data.mybatis.plugins.core.criteria.InsertQuery;
import com.meowu.starter.data.mybatis.plugins.core.criteria.UpdateQuery;

public class SqlProvider{

    public String save(InsertQuery query){
        return
            """
            <script>
            <![CDATA[
                INSERT INTO ${tableName}
                    <foreach collection="inserts" item="item" open="(" close=")" separator=",">
                        ${item.field.column}
                    </foreach>
                VALUE
                    <foreach collection="inserts" item="item" open="(" close=")" separator=",">
                        #{item.value, javaType=item.field.javaType, typeHandler=item.field.typeHandler}
                    </foreach>
            ]]>
            </script>
            """;
    }

    public String update(UpdateQuery query){
        return
            """
            <script>
            <![CDATA[
                UPDATE FROM ${tableName}
                <SET>
                    <foreach collection="updates" item="item" separator=",">
                        ${item.field.column} = #{item.value, javaType=item.field.javaType, typeHandler=item.field.typeHandler},
                    </foreach>
                </SET>
                <WHERE>
            
                </WHERE>
            ]]>
            </script>
            """;
    }
}
