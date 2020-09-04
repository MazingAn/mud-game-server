package com.mud.game.commands.character;

import com.mud.game.combat.NpcBoundItemInfo;
import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.ToastMessage;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.manager.WorldNpcObjectManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.util.Map;

/**
 * 拾取一个战利品
 * <p>
 * {
 * cmd: "pick_up_one",
 * args: {
 * "objectDataKey" : "<装备的dataKey>",
 * "npcDataKey" : "<装备的dataKey>",
 * }
 */
public class PickUpOne extends BaseCommand {
    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public PickUpOne(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        JSONObject args = getArgs();
        String npcDataKey = args.getString("npcDataKey");
        String objectDataKey = args.getString("objectDataKey");
        NpcBoundItemInfo npcBoundItemInfo = GameCharacterManager.npcBoundItemSet.get(npcDataKey);
        if (null == npcBoundItemInfo) {
            caller.msg(new ToastMessage("物品不存在"));
            return;
        }
        if (WorldNpcObjectManager.getTrophyCheckOne(caller, npcDataKey, npcBoundItemInfo, objectDataKey)) {
            //拾取物品到背包
            Map<String, Integer> dataKeyMap = npcBoundItemInfo.getNpcBoundItemMap();
            if (PlayerCharacterManager.receiveObjectToBagpack(caller, objectDataKey, dataKeyMap.get(objectDataKey))) {
                npcBoundItemInfo.getNpcBoundItemMap().remove(objectDataKey);
            }
            //战利品数据修改
            if (npcBoundItemInfo.getNpcBoundItemMap().size() == 0) {
                GameCharacterManager.npcBoundItemSet.remove(npcDataKey);
            } else {
                GameCharacterManager.npcBoundItemSet.put(npcDataKey, npcBoundItemInfo);
            }
        }
    }
}
