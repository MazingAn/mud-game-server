package com.mud.game.worlddata.web.admin.service;

import com.mud.game.utils.modelsutils.Mark;
import com.mud.game.worlddata.web.admin.structure.EntityInfo;
import com.mud.game.worlddata.web.admin.structure.FieldInfo;
import org.hibernate.mapping.Collection;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.lang.reflect.Field;
import static com.mud.game.utils.modelsutils.ClassUtils.getSortedFields;


/**
 * 实体类信息加载
 * */
public class EntityInfoLoader {

    /**
     *  加载一个实体类的字段信息
     * @param clazz 实体类的路径
     * @Return EntityInfo 包含实体类的基本信息和字段信息及字段限制信息
     * {@link EntityInfo}
     * */
    public static EntityInfo load(Class clazz){
        EntityInfo info = new EntityInfo();
        Mark mark = (Mark) clazz.getAnnotation(Mark.class);
        String entityName = mark.name();
        info.tableName = entityName;
        // 便利所有字段并获取字段信息
        for(Field field : getSortedFields(clazz)){
            String verboseName = field.getAnnotation(Mark.class).name();
            FieldInfo fieldInfo = new FieldInfo();
            fieldInfo.name = field.getName();
            fieldInfo.verboseName = verboseName;
            fieldInfo.type = field.getType();
            if(field.getAnnotation(Size.class) != null){
                fieldInfo.maxLength = field.getAnnotation(Size.class).max();
                fieldInfo.minLength = field.getAnnotation(Size.class).min();
                fieldInfo.message = field.getAnnotation(Size.class).message();
            }
            if(field.getAnnotation(NotNull.class) != null){
                fieldInfo.nullable = false;
            }
            if(field.getAnnotation(NotBlank.class) != null){
                fieldInfo.nullable = false;
                fieldInfo.message += ";" + field.getAnnotation(NotBlank.class).message();
            }
            info.fieldsInfo.add(fieldInfo);
        }
        return info;
    }
}