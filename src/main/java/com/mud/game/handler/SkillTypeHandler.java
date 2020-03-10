package com.mud.game.handler;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.SkillCategoryType;
import com.mud.game.worlddata.db.models.SkillFunctionType;

import java.util.HashMap;
import java.util.Map;

/**
 * 技能类型缓存类
 *
 * 这个类中定义的技能类型信息将在服务器启动的时候被初始化  参见：{@link com.mud.game.server.ServerManager}
 *
 * */
public class SkillTypeHandler {
    public static Map<String, String> functionTypeMapping = new HashMap<>();
    public static Map<String, String> categoryTypeMapping = new HashMap<>();

    /** 内部方法  初始化技能功能类型functionType信息 （从数据库一次性读取）  <br>
     *  如： SFT_ATTACK -> 攻击类技能
     * */
    private static void initSkillFunctionTypeHandler(){
        for(SkillFunctionType type: DbMapper.skillFunctionTypeRepository.findAll()){
            functionTypeMapping.put(type.getDataKey(), type.getName());
        }
    }

    /** 内部方法  初始化技能类别类型categoryType信息 （从数据库一次性读取）<br>
     *  如： SCT_BASIC -> 基本技能
     *  */
    private static void initSkillCategoryTypeHandler(){
        for(SkillCategoryType type: DbMapper.skillCategoryTypeRepository.findAll()){
            categoryTypeMapping.put(type.getDataKey(), type.getName());
        }
    }

    /** 外部方法  初始化类型缓存  这个方法总是会在服务器启动的时候被调用
     *  参见{@link com.mud.game.server.ServerManager} {@code start}*/
    public static void initSkillTypeHandler(){
        initSkillCategoryTypeHandler();
        initSkillFunctionTypeHandler();
    }
}
