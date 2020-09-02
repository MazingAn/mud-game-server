package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.ToastMessage;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.util.Map;

/**
 * 拾取战利品
 * <p>
 * {
 * cmd: "pick_up",
 * args: npc的dataKey
 * }
 */
public class PickUp extends BaseCommand {
    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public PickUp(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        JSONObject args = getArgs();
        // 装备id
        String npcDataKey = args.getString("args");
        Map<String, Integer> map = GameCharacterManager.npcBoundItemSet.get(npcDataKey);
        //TODO 尸体是否已复活、在固定时间内只有击杀者可以拾取
        if (null == map) {
            caller.msg(new ToastMessage("装备不存在"));
            return;
        }

        //TODO 校验背包空间是否足够
        //拾取物品到背包
        // 检查获取概率
        for (String key : map.keySet()) {
            PlayerCharacterManager.receiveObjectToBagpack(caller, key, map.get(key));
        }
    }
}
