package com.mud.game.handler;

import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.statements.skills.IncreamentsAttr;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class SkillFunctionHandler {
    private static Map<String, Class> skillFunctionSet = new HashMap<>();

    public static void execute(CommonCharacter caller, CommonCharacter target, SkillObject skillObject){
        String functionStr = skillObject.getSkillFunction();
        for(String function : functionStr.split(";")){
            String[] functionSplited = function.split("\\(");
            String key = functionSplited[0];
            String[] args = functionSplited[1].replaceAll("\\)", "").split(",");
            Class clazz = skillFunctionSet.get(key);
            try{
                Constructor c = clazz.getConstructor(CommonCharacter.class, CommonCharacter.class, SkillObject.class, String.class, String[].class);
                c.newInstance(caller, target, skillObject, key, args);
            }catch (Exception e){
                System.out.println(String.format("玩家在执行命令%s的时候触发了异常", key));
            }
        }
    }

    public static void initSkillFunctionSet(){
        skillFunctionSet.put("increaments_attr",IncreamentsAttr.class);
    }

}
