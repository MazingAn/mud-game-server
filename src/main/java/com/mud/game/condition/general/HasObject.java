package com.mud.game.condition.general;

import com.mud.game.condition.BaseCondition;
import com.mud.game.messages.MsgMessage;
import com.mud.game.messages.ToastMessage;
import com.mud.game.object.builder.CommonObjectBuilder;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.worlddata.db.models.supermodel.BaseCommonObject;

/**
 * 检查玩家身上是否有某一个物品
 *
 * <pre>
 *     录入形式： : has_object(“dahuandao”)
 *     输出： 玩家是否有key为dahuandao的物品
 * </pre>
 * */
public class HasObject extends BaseCondition {
    /**
     * 构造函数
     * <p>
     * 构造函数用于捕获参数
     *
     * @param key             检测类的健
     * @param playerCharacter 检测的主体对象（要被检测的玩家）
     * @param args            检测函数参数列表（字符串列表）
     */
    public HasObject(String key, PlayerCharacter playerCharacter, String[] args) {
        super(key, playerCharacter, args);
    }

    @Override
    public boolean match() throws NoSuchFieldException, IllegalAccessException {
        String[] args = getArgs();
        String objectDataKey = args[0];
        boolean result = PlayerCharacterManager.hasObject(getPlayerCharacter(), objectDataKey, 1);
        if(!result){
            BaseCommonObject template = CommonObjectBuilder.findObjectTemplateByDataKey(objectDataKey);
            if(template != null){
                getPlayerCharacter().msg(new MsgMessage(String.format("缺少物品{g%s{n", template.getName())));
                getPlayerCharacter().msg(new ToastMessage(String.format("缺少物品{g%s{n", template.getName())));
            }
        }
        return result;
    }
}
