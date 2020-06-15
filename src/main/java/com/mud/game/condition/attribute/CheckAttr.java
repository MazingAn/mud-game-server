package com.mud.game.condition.attribute;

import com.mud.game.condition.BaseCondition;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.typeclass.PlayerCharacter;

public class CheckAttr extends BaseCondition {
    /**
     * 构造函数
     * <p>
     * 构造函数用于捕获参数
     *
     * @param key             检测类的健
     * @param playerCharacter 检测的主体对象（要被检测的玩家）
     * @param args 函数所需参数（"属性名称", 检测值）
     */
    public CheckAttr(String key, PlayerCharacter playerCharacter, String[] args) {
        super(key, playerCharacter, args);
    }


    /**
     * 用于检测属性值是否相等
     *  参数列表：
     *       <pre>
     *           原型示例： check_attr("attack", "30"); //要求攻击等于30
     *           参数示例： String[] args = {"\"attack\"", "30"};
     *           获得玩家的 attack属性的值 然后进行比较
     *
     *
     *           原型示例： check_attr("gender", "不男不女"); //要求性别是不男不女
     *           参数示例： String[] args = {"\"gender\"", "不男不女"};
     *           获得玩家的 gender属性的值 然后进行比较
     *
     *
     *           原型示例： check_attr("doubleEquip", false); //能不能双持武器
     *           参数示例： String[] args = {"\"doubleEquip\"", false};
     *           获得玩家的 coubleEquip属性的值 然后进行比较
     *
     *      </pre>
     * */
    @Override
    public boolean match() throws NoSuchFieldException, IllegalAccessException {
        String[] args = getArgs();
        PlayerCharacter playerCharacter = getPlayerCharacter();
        String attrKey =  args[0].replaceAll("\"","");
        //  获取attrkey对应的值 根据这个值的类型来解析参数，然后比对

        Object originValue =  GameCharacterManager.findAttributeByName(playerCharacter, attrKey);

        if(originValue == null){
            return false;
        }

        if(originValue instanceof Integer){
            int value = Integer.parseInt(args[1]);
            return (int)originValue == value;
        }

        if(originValue instanceof Float){
            originValue = (int)originValue * 1F;
            float value = Float.parseFloat(args[1]);
            return (float) originValue == value;
        }

        if(originValue instanceof Boolean){
            return Boolean.parseBoolean(args[1]) == (boolean)originValue;
        }
        if(originValue instanceof String){
            return originValue.equals(args[1]);
        }

        return false;
    }
}
