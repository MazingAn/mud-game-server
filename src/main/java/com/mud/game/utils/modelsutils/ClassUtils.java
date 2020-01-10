package com.mud.game.utils.modelsutils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ClassUtils {
    public static List<String> getSortedFieldNames(Class<?> clazz) {
        /*
        * 寻找一个类的祖宗十八代，并按照第一代到最后一代的顺序排序其所有的属性
        * @ 这个功能好多人可能有生之年都不会用到
        * @ 但是本着精益求精的精神，写下了这个类
        * @ 它的功能是对于任意一个Java类，根据从最顶层父类，一直到自己的类继承顺序对其所有的属性名称进行排序
        * @ 这个类在这个项目里被用来 批量导出对客户友好的excel表格
        * */

        Class<?> superClass = clazz.getSuperclass();
        // orders 是一个用于对每一代所包含的属性的分段，也是最终的排序依据
        List<Integer> orders = new ArrayList<>();
        // 父类以及父类的父类一直到老祖宗所包含的所有属性，但不包含自己本身的属性
        List<String> unSortedNames = new ArrayList<>();
        // order是一个标志，他随着先祖的属性数量增加，但是orders里面只在每一代交替的时候才写入，作为分段
        int order = 0;
        orders.add(order);
        // 递归获取父类的属性
        while(superClass != null){
            for(Field field: superClass.getDeclaredFields()){
                unSortedNames.add(field.getName());
                order++;
            }
            orders.add(order);
            superClass = superClass.getSuperclass();
        }

        // 获取自己的属性,自己的属性在排序的时候一定处于最后一部分
        List<String> lastPartNames = new ArrayList<>();
        for(Field field : clazz.getDeclaredFields()){
            lastPartNames.add(field.getName());
        }

        // 最终我们想要的 从第一代到自己这一代的所有属性的有序（代序）集合
        List<String> names = new ArrayList<>();
        // 分段标志数组 orders 把一代代的先祖分开，从后往前追加即为有序
        for(int i = orders.size()-1; i > 0; i--){
            names.addAll(unSortedNames.subList(orders.get(i-1) , orders.get(i)));
        }
        // 最后再把自己这一带本身的属性追加上去
        names.addAll(lastPartNames);

        System.out.println(names);

        return names;
    }


    public static List<Object> getSortedValue(Class<?> clazz, Object instance) throws IllegalAccessException {
        Class<?> superClass = clazz.getSuperclass();
        // orders 是一个用于对每一代所包含的属性的分段，也是最终的排序依据
        List<Integer> orders = new ArrayList<>();
        // 父类以及父类的父类一直到老祖宗所包含的所有属性，但不包含自己本身的属性
        List<Object> unsortedValues = new ArrayList<>();
        // order是一个标志，他随着先祖的属性数量增加，但是orders里面只在每一代交替的时候才写入，作为分段
        int order = 0;
        orders.add(order);
        // 递归获取父类的属性值
        while(superClass != null){
            for(Field field: superClass.getDeclaredFields()){
                field.setAccessible(true);
                unsortedValues.add(field.get(instance));
                order++;
            }
            orders.add(order);
            superClass = superClass.getSuperclass();
        }

        // 获取自己的属性,自己的属性在排序的时候一定处于最后一部分
        List<String> lastPartValues = new ArrayList<>();
        for(Field field : clazz.getDeclaredFields()){
            lastPartValues.add(field.getName());
        }

        // 最终我们想要的 从第一代到自己这一代的所有属性的有序（代序）集合
        List<Object> values = new ArrayList<>();
        // 分段标志数组 orders 把一代代的先祖分开，从后往前追加即为有序
        for(int i = orders.size()-1; i > 0; i--){
            values.addAll(unsortedValues.subList(orders.get(i-1) , orders.get(i)));
        }
        // 最后再把自己这一带本身的属性追加上去
        values.addAll(lastPartValues);

        System.out.println(values);

        return values;
    }
}
