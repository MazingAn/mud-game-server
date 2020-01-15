package com.mud.game.handler;

import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.statements.skills.IncrementsAttr;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class SkillFunctionHandler {
    private static Map<String, Class> actionSkillFunctionSet = new HashMap<>();
    private static Map<String, Class> passiveSkillFunctionSet = new HashMap<>();

    public static void calculusEffect(CommonCharacter caller, CommonCharacter target, SkillObject skillObject){
        String functionStr = skillObject.getSkillFunction();
        if(functionStr == null || functionStr.trim().equals("")){
            return;
        }
        for(String function : functionStr.split(";")){
            String[] functionSplited = function.split("\\(");
            String key = functionSplited[0].replaceAll("\"", "").replaceAll("\\'", "");
            String[] args = functionSplited[1].replaceAll("\\)", "").replaceAll("\\'","").replaceAll("\"", "").split(",");
            // 根据技能是否是被动技能选择对应的functionSet；对于被动技能只返回技能属性加成
            // 对于主动技能，直接执行
            Class clazz = null;
            if(skillObject.isPassive()) clazz = passiveSkillFunctionSet.get(key);
            else clazz = actionSkillFunctionSet.get(key);
            try{
                Constructor c = clazz.getConstructor(CommonCharacter.class, CommonCharacter.class, SkillObject.class, String.class, String[].class);
                c.newInstance(caller, target, skillObject, key, args);
            }catch (Exception e){
                System.out.println(String.format("玩家在执行命令%s的时候触发了异常", key));
            }
        }
    }

    public static void initSkillFunctionSet(){
        initPassiveSkillFunctionSet();
        initActionSkillFunctionSet();
    }

    public static void initPassiveSkillFunctionSet(){
        // 所有被动技能的实现映射
        passiveSkillFunctionSet.put("increaments_attr", IncrementsAttr.class);
    }

    public static void initActionSkillFunctionSet(){
        // 所有主动技能的实现映射
    }

}
