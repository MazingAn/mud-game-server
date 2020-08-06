package com.mud.game.handler;

import com.mud.game.condition.BaseCondition;
import com.mud.game.condition.attribute.AttrGt;
import com.mud.game.condition.attribute.AttrLt;
import com.mud.game.condition.attribute.CheckAttr;
import com.mud.game.condition.attribute.UnCheckAttr;
import com.mud.game.condition.general.*;
import com.mud.game.net.session.GameSessionService;
import com.mud.game.object.typeclass.PlayerCharacter;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * 游戏中的动态判断
 * 游戏管理员可以在配置中以字符串的形式存储一些函数<br>
 * */
public class ConditionHandler {


    /** 游戏内所有的condition集合  key为字符串类型 value为对应的判断执行类的路径 */
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
     * @return boolean 校验总体是否通过
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
                String[] args = sourceSplited[1].replaceAll("\\)", "").replaceAll("\"", "").split(",");
                Class clazz = conditionSet.get(key);
                try {
                    Constructor<BaseCondition> c = clazz.getConstructor(String.class, PlayerCharacter.class, String[].class);
                    BaseCondition condition = c.newInstance(key, playerCharacter, args);
                    if(!condition.match()){
                        result = false;
                        break;
                    }
                }catch ( Exception e){
                    System.out.println(String.format("条件判定执行失败：%s, %s", key, conditionStr) );
                    return false;
                }
            }
        }catch (Exception e){
            System.out.println(String.format("条件判定执行失败：%s", conditionStr) );
            result = false;
        }
        return result;
    }


    /** 初始化condition集合，所有定义好的condition类都在这里被注册并通过这个集合反射到对应的执行主体 */
    public static void initConditionHandler(){
        conditionSet.put("check_attr", CheckAttr.class);
        conditionSet.put("attr_lt", AttrLt.class);
        conditionSet.put("attr_gt", AttrGt.class);
        conditionSet.put("check_number", CheckNumber.class);
        conditionSet.put("has_object", HasObject.class);
        conditionSet.put("no_object", NoObject.class);
        conditionSet.put("is_npc_died", IsNpcDied.class);
        conditionSet.put("uncheck_attr", UnCheckAttr.class);
        conditionSet.put("never_unlock", NeverUnlock.class);
        conditionSet.put("check_room_step", CheckRoomStep.class);
        conditionSet.put("has_skill", HasSkill.class);
        conditionSet.put("has_equip_weapon_name", HasEquippedWeaponName.class);
    }

}
