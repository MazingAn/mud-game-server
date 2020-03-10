package com.mud.game.commands.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.MsgMessage;
import com.mud.game.object.account.Player;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

/**
 * 请求添加好友
 *
 * 请求示例：
 * <pre>
 *      {
 *          "cmd" : "add_friend",
 *          "args" : "要添加的人的ID"
 *      }
 * </pre>
 *
 * */
public class RequestFriend extends BaseCommand {

    public RequestFriend(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException, JsonProcessingException {
        /*
        * 根据对方的id添加好友
        * 运行命令之前，先检测玩家id和对方的id是否已经存在
        * 也就是手动的进行unique_together限制
        * */
        Session session = getSession();
        PlayerCharacter playerCharacter = (PlayerCharacter) getCaller();
        String targetId = getArgs().getString("args");
        PlayerCharacterManager.requestFriend(playerCharacter, targetId, session);
    }
}
