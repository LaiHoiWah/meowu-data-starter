package com.meowu.starter.data.mybatis.plugins.utils;

import com.google.common.collect.Lists;
import com.meowu.starter.commons.utils.SpellUtils;
import com.meowu.starter.data.mybatis.plugins.commons.entity.FieldInfo;
import com.meowu.starter.data.mybatis.plugins.commons.entity.MapperInfo;
import com.meowu.starter.data.mybatis.plugins.security.exception.ReflectionException;
import com.meowu.starter.data.mybatis.plugins.security.stereotype.Fields;
import com.meowu.starter.data.mybatis.plugins.security.stereotype.Table;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class MapperUtils{

    private static final ConcurrentHashMap<String, MapperInfo> MAPPER_CACHE = new ConcurrentHashMap<String, MapperInfo>();

    public static MapperInfo getMapperInfo(Object mapper){
        Class<?> typeOf =  mapper.getClass();
        String className = typeOf.getName();

        // get from cache
        MapperInfo mapperInfo = MAPPER_CACHE.get(className);
        if(Objects.nonNull(mapperInfo)){
            return mapperInfo;
        }

        // create cache
        String tableName = getTableName(mapper);
        List<FieldInfo> fields = getFieldInfo(mapper);

        // caching
        mapperInfo = new MapperInfo();
        mapperInfo.setMapper(mapper);
        mapperInfo.setTableName(tableName);
        mapperInfo.setFields(fields);
        MAPPER_CACHE.put(className, mapperInfo);

        return mapperInfo;
    }

    public static MapperInfo getMapperInfoWithFieldValue(Object mapper){
        try{
            // get mapper info
            MapperInfo mapperInfo = getMapperInfo(mapper);
            // clone for filling values
            MapperInfo clone = ObjectUtils.clone(mapperInfo);

            // do reflect
            Class<?> typeOf = mapper.getClass();
            Field[] fields = typeOf.getDeclaredFields();
            List<FieldInfo> fieldInfos = clone.getFields();

            // set values
            if(ArrayUtils.isNotEmpty(fields) && CollectionUtils.isNotEmpty(fieldInfos)){
                for(FieldInfo info : fieldInfos){
                    // property name
                    String property = info.getProperty();
                    // mapping
                    for(Field field : fields){
                        String fieldName = field.getName();
                        if(fieldName.equals(property)){
                            // get access right
                            field.setAccessible(true);
                            info.setValue(field.get(mapper));
                        }
                    }
                }
            }

            return clone;
        }catch(Exception e){
            throw new ReflectionException(e.getMessage(), e);
        }
    }

    public static String getTableName(Object mapper){
        Class<?> typeOf = mapper.getClass();

        // get table annotation
        Table annotation = typeOf.getAnnotation(Table.class);
        if(Objects.nonNull(annotation) && StringUtils.isNotBlank(annotation.value())){
            // annotation value
            return annotation.value();
        }

        // class name for underscore case
        return SpellUtils.toUnderScore(typeOf.getSimpleName());
    }

    public static List<FieldInfo> getFieldInfo(Object mapper){
        try{
            // result
            List<FieldInfo> fieldInfos = Lists.newArrayList();

            // reflect
            Class<?> typeOf = mapper.getClass();
            Field[] fields = typeOf.getDeclaredFields();

            if(ArrayUtils.isNotEmpty(fields)){
                for(Field field : fields){
                    // base info
                    FieldInfo info = new FieldInfo();
                    info.setProperty(field.getName());
                    info.setJavaType(field.getType().getName());

                    // get annotation
                    Fields annotation = field.getAnnotation(Fields.class);

                    // field name
                    if(Objects.nonNull(annotation) && StringUtils.isNotBlank(annotation.value())){
                        info.setColumn(annotation.value());
                    }else{
                        info.setColumn(SpellUtils.toUnderScore(field.getName()));
                    }

                    // field handlerType
                    if(Objects.nonNull(annotation) && Objects.nonNull(annotation.typeHandler())){
                        info.setTypeHandler(annotation.typeHandler().getName());
                    }

                    // add to list
                    fieldInfos.add(info);
                }
            }

            return fieldInfos;
        }catch(Exception e){
            throw new ReflectionException();
        }
    }
}
