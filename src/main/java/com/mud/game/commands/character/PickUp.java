package com.mud.game.commands.character;

import com.mud.game.combat.NpcBoundItemInfo;
import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.ToastMessage;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.manager.WorldNpcObjectManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.WorldNpcObject;
import com.mud.game.utils.collections.ListUtils;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.util.ArrayList;
import java.util.List;
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
        // npc的dataKey
        String npcDataKey = args.getString("args");
        NpcBoundItemInfo npcBoundItemInfo = GameCharacterManager.npcBoundItemSet.get(npcDataKey);
        if (null == npcBoundItemInfo) {
            caller.msg(new ToastMessage("物品不存在"));
            return;
        }
        if (WorldNpcObjectManager.getTrophyCheck(caller, npcDataKey, npcBoundItemInfo)) {
            //拾取物品到背包
            // 检查获取概率
            Map<String, Integer> npcBoundItemMap = npcBoundItemInfo.getNpcBoundItemMap();
            List<Integer> list = new ArrayList<>();
            int index = 0;
            for (String key : npcBoundItemMap.keySet()) {
                if (PlayerCharacterManager.receiveObjectToBagpack(caller, key, npcBoundItemMap.get(key))) {
                    list.add(index);
                }
                index++;
            }
            for (int i = 0; i < list.size(); i++) {
                npcBoundItemMap.remove(list.get(i));
            }
            //战利品数据修改
            if (npcBoundItemMap.size() == 0) {
                GameCharacterManager.npcBoundItemSet.remove(npcDataKey);
            } else {
                npcBoundItemInfo.setNpcBoundItemMap(npcBoundItemMap);
                GameCharacterManager.npcBoundItemSet.put(npcDataKey, npcBoundItemInfo);
            }
        }
    }
}
