package com.mud.game.condition.general;

import com.mud.game.condition.BaseCondition;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.WorldNpcObject;
import com.mud.game.worldrun.db.mappings.MongoMapper;

/**
 * 检查NPC是否死亡
 * 参数列表：
 *  <pre>
 *  原型示例： is_npc_died("XIJING_WUGUA_YANGLAOBAN");
 *  获得杨老板的实例 并检查是否死亡
 *  </pre>
 *
 * */
public class IsNpcDied extends BaseCondition {
    /**
     * 构造函数
     * 构造函数用于捕获参数
     *
     * @param key             检测类的健
     * @param playerCharacter 检测的主体对象（要被检测的玩家）
     * @param args            检测函数参数列表（字符串列表）
     */
    public IsNpcDied(String key, PlayerCharacter playerCharacter, String[] args) {
        super(key, playerCharacter, args);
    }

    @Override
    public boolean match() throws NoSuchFieldException, IllegalAccessException {
        String[] args = getArgs();
        String dataKey = args[0];
        WorldNpcObject npc = MongoMapper.worldNpcObjectRepository.findWorldNpcObjectByDataKey(dataKey);
        return npc.getHp() <= 0;
    }
}
