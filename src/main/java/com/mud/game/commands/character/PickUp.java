package com.mud.game.commands.character;

import com.mud.game.combat.NpcBoundItemInfo;
import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.ToastMessage;
import com.mud.game.object.builder.CommonObjectBuilder;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.manager.WorldNpcObjectManager;
import com.mud.game.object.supertypeclass.CommonObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.WorldNpcObject;
import com.mud.game.utils.collections.ListUtils;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang.StringUtils.isNumeric;

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
        String dbref = args.getString("args");
        NpcBoundItemInfo npcBoundItemInfo = GameCharacterManager.npcBoundItemSet.get(dbref);
        if (null == npcBoundItemInfo) {
            caller.msg(new ToastMessage("物品不存在"));
            return;
        }
        WorldNpcObject worldNpcObject = MongoMapper.worldNpcObjectRepository.findWorldNpcObjectByDataKey(dbref);
        List<String> list = new ArrayList<>();
        if (WorldNpcObjectManager.getTrophyCheck(caller, worldNpcObject, npcBoundItemInfo)) {
            //拾取物品到背包
            // 检查获取概率
            Map<String, Integer> npcBoundItemMap = npcBoundItemInfo.getNpcBoundItemMap();
            int index = 0;
            for (String key : npcBoundItemMap.keySet()) {
                //判断是模板物品还是唯一物品
                if (isNumeric(key)) {
                    if (PlayerCharacterManager.receiveObjectToBagpack(caller, key, npcBoundItemMap.get(key))) {
                        npcBoundItemInfo.getNpcBoundItemMap().remove(key);
                        list.add(key);
                    }
                } else {
                    CommonObject commonObject = CommonObjectBuilder.findObjectById(key);
                    if (PlayerCharacterManager.receiveObjectToBagpack(caller, commonObject, npcBoundItemMap.get(key))) {
                        npcBoundItemInfo.getNpcBoundItemMap().remove(key);
                        list.add(key);
                    }
                }
                index++;
            }
            for (int i = 0; i < list.size(); i++) {
                npcBoundItemMap.remove(list.get(i));
            }
            //战利品数据修改
            if (npcBoundItemMap.size() == 0) {
                list = new ArrayList<>();
                GameCharacterManager.npcBoundItemSet.remove(dbref);
            } else {
                npcBoundItemInfo.setNpcBoundItemMap(npcBoundItemMap);
                GameCharacterManager.npcBoundItemSet.put(dbref, npcBoundItemInfo);
            }
            //返回数据
            List<String> finalList = list;
            caller.msg(new HashMap<String, Object>() {{
                put("remove_pick_up_one", finalList);
            }});
        }
    }
}
