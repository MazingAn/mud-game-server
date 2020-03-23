package com.mud.game.utils.modelsutils;

import java.lang.annotation.*;

/**
 * 注解一个实体类的中文名称和字段中文名称
 * */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)@Inherited
public @interface Mark {
    String name() default "";
}
