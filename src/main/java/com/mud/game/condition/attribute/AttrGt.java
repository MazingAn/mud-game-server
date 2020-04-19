package com.mud.game.condition.attribute;

import com.mud.game.condition.BaseCondition;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.typeclass.PlayerCharacter;


/**
 * 玩家属性检测类 检测玩家属性是否大于某一个值
 * */

public class AttrGt extends BaseCondition {

    public AttrGt(String key, PlayerCharacter playerCharacter, String[] args) {
        super(key, playerCharacter, args);
    }

    /**
     * 匹配函数
     *
     * 参数列表：
     * <pre>
     *     原型示例： attr_gt("attack", "30"); //要求攻击大于30
     *     参数示例： String[] args = {"\"attack\"", "30"};
     *     获得玩家的 attack属性的值 然后进行比较
     * </pre>
     *
     * */
    @Override
    public boolean match() throws NoSuchFieldException, IllegalAccessException {
        String[] args = getArgs();
        String attrKey = args[0].replaceAll("\"", "");
        PlayerCharacter playerCharacter = getPlayerCharacter();
        float matchValue = Float.parseFloat(args[1]);
        Object originValue =  GameCharacterManager.findAttributeByName(playerCharacter, attrKey) ;
        if(originValue == null){
            return false;
        }
        if(originValue instanceof Integer){
            // 如果比较的数据类型是Int类型
            originValue = (int)originValue * 1F;
        }
        return (float)originValue > matchValue;
    }

}
