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
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.util.Map;

/**
 * 卸掉称号
 *
 * <pre>
 *    {
 *        "cmd": "take_off_title",
 *        "args": "titleDataKey", //称号标识
 *    }
 *  </pre>
 */
public class TakeOffTitle extends BaseCommand {
    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public TakeOffTitle(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        JSONObject args = getArgs();
        if (null == args) {
            caller.msg("无效的参数！");
        }
        // 称号标识
        String dataKey = args.getString("dataKey");
        //卸掉之前的称号
        PlayerTitle oldPlayerTitle = DbMapper.playerTitleRepository.findPlayerTitleByDataKey(caller.getTitle());
        Map<String, Map<String, Object>> oldMap = Attr2Map.equipmentAttrTrans(oldPlayerTitle.getEffect());
        for (String attrKey : oldMap.keySet()) {
            Object valueStr = oldMap.get(attrKey).get("value");
            float value = Float.parseFloat(valueStr.toString());
            GameCharacterManager.changeStatus(caller, attrKey, value * -1);
        }
        caller.setTitle(null);
        MongoMapper.playerCharacterRepository.save(caller);
        PlayerCharacterManager.showStatus(caller);
        //发送玩家的称号信息
        caller.msg(new TitlesMessage(caller));
    }
}
