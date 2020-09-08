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
        String dbref = args.getString("npcDbref");
        String goodDbref = args.getString("goodDbref");
        //TODO 首先创建装备，设置锻造属性
        WorldNpcObject worldNpcObject = MongoMapper.worldNpcObjectRepository.findWorldNpcObjectById(dbref);
        NpcBoundItemInfo npcBoundItemInfo = GameCharacterManager.npcBoundItemSet.get(dbref);
        if (null == npcBoundItemInfo) {
            caller.msg(new ToastMessage("物品不存在"));
            return;
        }
        //TODO 返回已拾取装备
        List<String> list = new ArrayList<>();
        if (WorldNpcObjectManager.getTrophyCheckOne(caller, worldNpcObject, npcBoundItemInfo, goodDbref)) {
            //拾取物品到背包
            Map<String, Integer> dataKeyMap = npcBoundItemInfo.getNpcBoundItemMap();
            //判断是模板物品还是唯一物品
            if (isNumeric(goodDbref)) {
                if (PlayerCharacterManager.receiveObjectToBagpack(caller, goodDbref, dataKeyMap.get(goodDbref))) {
                    npcBoundItemInfo.getNpcBoundItemMap().remove(goodDbref);
                    list.add(goodDbref);
                }
            } else {
                CommonObject commonObject = CommonObjectBuilder.findObjectById(goodDbref);
                if (PlayerCharacterManager.receiveObjectToBagpack(caller, commonObject, dataKeyMap.get(goodDbref))) {
                    npcBoundItemInfo.getNpcBoundItemMap().remove(goodDbref);
                    list.add(goodDbref);
                }
            }
            //战利品数据修改
            if (npcBoundItemInfo.getNpcBoundItemMap().size() == 0) {
                list = new ArrayList<>();
                GameCharacterManager.npcBoundItemSet.remove(dbref);
            } else {
                GameCharacterManager.npcBoundItemSet.put(dbref, npcBoundItemInfo);
            }
            //返回数据
            caller.msg(new HashMap<String, String>() {{
                put("remove_pick_up_one", dbref);
            }});
        }

    }
}
