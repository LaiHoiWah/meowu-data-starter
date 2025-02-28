package com.meowu.starter.data.mybatis.plugins.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.meowu.starter.commons.utils.SpellUtils;
import com.meowu.starter.data.mybatis.plugins.commons.entity.FieldData;
import com.meowu.starter.data.mybatis.plugins.commons.entity.FieldInfo;
import com.meowu.starter.data.mybatis.plugins.commons.entity.Metadata;
import com.meowu.starter.data.mybatis.plugins.commons.entity.StructureInfo;
import com.meowu.starter.data.mybatis.plugins.security.exception.ReflectionException;
import com.meowu.starter.data.mybatis.plugins.security.stereotype.Fields;
import com.meowu.starter.data.mybatis.plugins.security.stereotype.Table;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class MetadataUtils{

    private static final ConcurrentHashMap<String, StructureInfo> STRUCTURE_CACHE = new ConcurrentHashMap<String, StructureInfo>();

    public static Metadata getMetadata(Object entity){
        if(Objects.isNull(entity)){
            throw new ReflectionException("Meta object must not be null");
        }

        // get type
        Class<?> typeOf = entity.getClass();
        // get structure
        StructureInfo structure = getStructure(typeOf);
        // get field data
        List<FieldData> fieldData = getFieldData(entity, structure.getFields());

        // result
        Metadata metadata = new Metadata();
        metadata.setStructure(structure);
        metadata.setFieldData(fieldData);

        return metadata;
    }

    public static StructureInfo getStructure(Class<?> typeOf){
        if(Objects.isNull(typeOf)){
            throw new ReflectionException("Type must not be null");
        }

        // get class name
        String className = typeOf.getName();
        // get from cache
        StructureInfo structure = STRUCTURE_CACHE.get(className);
        if(structure == null){
            structure = new StructureInfo();
            structure.setTableName(getTableName(typeOf));
            structure.setFields(getFieldInfo(typeOf));
            STRUCTURE_CACHE.put(className, structure);
        }

        return structure;
    }

    public static String getTableName(Class<?> typeOf){
        if(Objects.isNull(typeOf)){
            throw new ReflectionException("Type must not be null");
        }

        // get table annotation
        Table annotation = typeOf.getAnnotation(Table.class);
        if(Objects.nonNull(annotation) && StringUtils.isNotBlank(annotation.value())){
            // annotation value
            return annotation.value();
        }

        // class name for underscore case
        return SpellUtils.toUnderScore(typeOf.getSimpleName());
    }

    public static List<FieldInfo> getFieldInfo(Class<?> typeOf){
        if(Objects.isNull(typeOf)){
            throw new ReflectionException("Type must not be null");
        }

        try{
            // result
            List<FieldInfo> fieldInfos = Lists.newArrayList();
            // reflect
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
            throw new ReflectionException(e.getMessage(), e);
        }
    }

    public static List<FieldData> getFieldData(Object entity, List<FieldInfo> fields){
        if(Objects.isNull(entity)){
            throw new ReflectionException("Meta object must not be null");
        }
        if(CollectionUtils.isEmpty(fields)){
            throw new ReflectionException("Field info list must not be null");
        }

        try{
            // class
            Class<?> typeOf = entity.getClass();
            // result
            List<FieldData> fieldData = Lists.newArrayList();

            // foreach
            for(FieldInfo field : fields){
                String fieldName = field.getProperty();
                Field declaredField = typeOf.getDeclaredField(fieldName);
                // set accessible
                declaredField.setAccessible(true);
                // get value
                Object value = declaredField.get(entity);
                // build data
                FieldData data = new FieldData();
                data.setField(field);
                data.setValue(value);
                // add into result
                fieldData.add(data);
            }

            return fieldData;
        }catch(Exception e){
            throw new ReflectionException(e.getMessage(), e);
        }
    }
}
