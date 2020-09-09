package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.TitlesMessage;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.utils.jsonutils.Attr2Map;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.PlayerTitle;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.util.Map;

/**
 * 装备称号
 *
 * <pre>
 *    {
 *        "cmd": "equipment_title",
 *        "args": "titleDataKey", //称号标识
 *    }
 *  </pre>
 */
public class EquipmentTitle extends BaseCommand {
    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public EquipmentTitle(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        JSONObject args = getArgs();
        if (null == args) {
            caller.msg("无效的参数！");
            return;
        }
        // 称号标识
        String dataKey = args.getString("dataKey");
//        if (null != caller.getTitleKeySet() && !caller.getTitleKeySet().contains(dataKey)) {
//            caller.msg("装备称号失败！");
//            return;
//        }
        PlayerTitle playerTitle = DbMapper.playerTitleRepository.findPlayerTitleByDataKey(dataKey);
        if (null != playerTitle.getEffect()) {
            Map<String, Map<String, Object>> mapMap = Attr2Map.equipmentAttrTrans(playerTitle.getEffect());
            if (StringUtils.isNotBlank(caller.getTitle())) {
                //卸掉之前的称号
                PlayerTitle oldPlayerTitle = DbMapper.playerTitleRepository.findPlayerTitleByDataKey(caller.getTitle());
                Map<String, Map<String, Object>> oldMap = Attr2Map.equipmentAttrTrans(oldPlayerTitle.getEffect());
                for (String attrKey : oldMap.keySet()) {
                    Object valueStr = oldMap.get(attrKey).get("value");
                    float value = Float.parseFloat(valueStr.toString());
                    GameCharacterManager.changeStatus(caller, attrKey, value * -1);
                }
                //装备现在的
                equipmentTitle(caller, dataKey, mapMap);
            } else {
                //直接装备称号
                equipmentTitle(caller, dataKey, mapMap);
            }
        }
        MongoMapper.playerCharacterRepository.save(caller);
        PlayerCharacterManager.showStatus(caller);
        //发送玩家的称号信息
        caller.msg(new TitlesMessage(caller));
    }

    private void equipmentTitle(PlayerCharacter caller, String dataKey, Map<String, Map<String, Object>> mapMap) {
        //装备称号
        caller.setTitle(dataKey);
        for (String attrKey : mapMap.keySet()) {
            // 找到对应的属性名字 对应的数值
            Object value = mapMap.get(attrKey).get("value");
            // 应用属性名字和对应的数值到角色身上
            GameCharacterManager.changeStatus(caller, attrKey, value);
        }
    }
}
