package com.mud.game.commands.character;

import com.mud.game.combat.NpcBoundItemInfo;
import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.ToastMessage;
import com.mud.game.object.builder.CommonObjectBuilder;
import com.mud.game.object.builder.MailObjectBuilder;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.manager.WorldNpcObjectManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.structs.AttachmentInfo;
import com.mud.game.worlddata.db.models.supermodel.BaseCommonObject;
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
 * args: npc的dataKey
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
        String npcDataKey = args.getString("args");
        NpcBoundItemInfo npcBoundItemInfo = GameCharacterManager.npcBoundItemSet.get(npcDataKey);
        if (null == npcBoundItemInfo) {
            caller.msg(new HashMap<String, Object>() {{
                put("puck_up_list", null);
            }});
            return;
        }
        //拿到战利品
        Map<String, Integer> npcBoundItemMap = npcBoundItemInfo.getNpcBoundItemMap();
        if (null == npcBoundItemMap) {
            caller.msg(new HashMap<String, Object>() {{
                put("puck_up_list", null);
            }});
            return;
        }
        //不为空
        List<AttachmentInfo> attachmentInfoList = new ArrayList<>();
        BaseCommonObject baseCommonObject = null;
        for (String str : npcBoundItemMap.keySet()) {
            baseCommonObject = CommonObjectBuilder.findObjectTemplateByDataKey(str);
            if (null != baseCommonObject) {
                 attachmentInfoList.add(MailObjectBuilder.getMailObjectInfo(baseCommonObject, npcBoundItemMap.get(str)));
            }
        }
        caller.msg(new HashMap<String, Object>() {{
            put("puck_up_list", attachmentInfoList);
        }});
    }
}
