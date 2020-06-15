package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.object.account.Player;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.WorldNpcObject;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

/**
 * 玩家展开一段对话
 * Usage:
 * <pre>
 *    {
 *        "cmd": "talk",
 *        "args": "npcId"
 *    }
 *  </pre>
 * */

public class Talk extends BaseCommand {
    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public Talk(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }


    @Override
    public void execute() throws JSONException {
        String npcId = getArgs().getString("args");
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        WorldNpcObject npc = MongoMapper.worldNpcObjectRepository.findWorldNpcObjectById(npcId);
        PlayerCharacterManager.talkToNpc(caller, npc);
    }
}
