package com.mud.game.handler;

import com.mud.game.condition.BaseCondition;
import com.mud.game.condition.attribute.AttrGt;
import com.mud.game.condition.general.CheckNumber;
import com.mud.game.object.typeclass.PlayerCharacter;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class ConditionHandler {
    /*
    * @游戏中的动态判断
    * @游戏管理员可以在配置中便携一些函数
    * @这里需要解析这些函数 然后执行
    * */

    public static Map<String, Class<?>> conditionSet = new HashMap<>();

    public static boolean matchCondition(String conditionStr, PlayerCharacter playerCharacter){
        if(conditionStr.trim().equals("")){
            return true;
        }
        boolean result = false;
        try{
            String[] conditionSources = conditionStr.split(";");
            for(String source : conditionSources){
                String[] sourceSplited = source.split("\\(");
                String key = sourceSplited[0];
                String[] args = sourceSplited[1].replaceAll("\\)", "").split(",");
                Class clazz = ConditionHandler.conditionSet.get(key);
                try {
                    Constructor<BaseCondition> c = clazz.getConstructor(String.class, PlayerCharacter.class, String[].class);
                    BaseCondition condition = c.newInstance(key, playerCharacter, args);
                    if(condition.match()){
                        result = true;
                        break;
                    }
                }catch ( Exception e){
                    e.printStackTrace();
                    return false;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return result;
    }

    public static void initConditionHandler(){
        conditionSet.put("attr_gt", AttrGt.class);
        conditionSet.put("check_number", CheckNumber.class);
    }

}
