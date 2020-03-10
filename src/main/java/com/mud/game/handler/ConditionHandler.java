package com.mud.game.handler;

import com.mud.game.condition.BaseCondition;
import com.mud.game.condition.attribute.AttrGt;
import com.mud.game.condition.attribute.AttrLt;
import com.mud.game.condition.attribute.CheckAttr;
import com.mud.game.condition.general.CheckNumber;
import com.mud.game.net.session.GameSessionService;
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

    /**
     * 进行匹配（条件校验）
     *
     * 接收一个校验函数字符串，解析字符串并返回对应校验函数的校验结果 true通过/false不通过
     *
     * @param conditionStr  一个函数校验字符串 如: "check_attr("attack", 30);attr_gt("max_hp", 300)"
     *                      要求 攻击等于30且最大气血大于300
     * @param playerCharacter 被校验的玩家
     *
     * */
    public static boolean matchCondition(String conditionStr, PlayerCharacter playerCharacter){
        if(conditionStr.trim().equals("")){
            return true;
        }
        boolean result = true;
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
                    if(!condition.match()){
                        result = false;
                        break;
                    }
                }catch ( Exception e){
                    e.printStackTrace();
                    return false;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public static void initConditionHandler(){
        conditionSet.put("check_attr", CheckAttr.class);
        conditionSet.put("attr_lt", AttrLt.class);
        conditionSet.put("attr_gt", AttrGt.class);
        conditionSet.put("check_number", CheckNumber.class);
    }

}
