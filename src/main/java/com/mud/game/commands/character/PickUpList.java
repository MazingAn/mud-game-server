package com.mud.game.commands.character;

import com.mud.game.combat.NpcBoundItemInfo;
import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.ToastMessage;
import com.mud.game.object.builder.CommonObjectBuilder;
import com.mud.game.object.builder.MailObjectBuilder;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.supertypeclass.CommonObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.WorldNpcObject;
import com.mud.game.structs.AttachmentInfo;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 战利品列表
 * <p>
 * {
 * cmd: "pick_up_list",
 * args: npc的dbref
 * }
 */
public class PickUpList extends BaseCommand {
    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public PickUpList(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        JSONObject args = getArgs();
        // 装备id
        String dbref = args.getString("args");
        NpcBoundItemInfo npcBoundItemInfo = GameCharacterManager.npcBoundItemSet.get(dbref);
        WorldNpcObject worldNpcObject = MongoMapper.worldNpcObjectRepository.findWorldNpcObjectById(dbref);
        if (null == npcBoundItemInfo) {
            caller.msg(new ToastMessage("{g" + worldNpcObject.getName() + "的尸体上没什么都没有！{n"));
            return;
        }
        //拿到战利品
        Map<String, Integer> npcBoundItemMap = npcBoundItemInfo.getNpcBoundItemMap();
        if (null == npcBoundItemMap || npcBoundItemMap.size() == 0) {
            caller.msg(new ToastMessage("{g" + worldNpcObject.getName() + "的尸体上没什么都没有！{n"));
            return;
        }
        //不为空
        List<AttachmentInfo> attachmentInfoList = new ArrayList<>();
        CommonObject commonObject = null;
        for (String str : npcBoundItemMap.keySet()) {
            commonObject = CommonObjectBuilder.findObjectById(str);
            if (null != commonObject) {
                attachmentInfoList.add(MailObjectBuilder.getMailObjectInfo(commonObject, npcBoundItemMap.get(str)));
            }
        }
        caller.msg(new HashMap<String, Object>() {{
            put("puck_up_list", attachmentInfoList);
        }});
    }
}
