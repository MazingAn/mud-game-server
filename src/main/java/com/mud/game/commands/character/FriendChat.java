package com.mud.game.commands.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.MsgMessage;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.utils.jsonutils.JsonResponse;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;


/**
 * 玩家和好友聊天（转发玩家的消息给其他玩家）
 * 请求示例:
 * <pre>
 *    {
 *        "cmd": "friend_chat",
 *        "args": {
 *           "name": "name", //朋友名称
 *           "message": "bla-bla-~~~" //消息内容
 *        }
 *    }
 * </pre>
 */
public class FriendChat extends BaseCommand {

    public FriendChat(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        Session session = getSession();
        JSONObject args = getArgs();
        String name = args.getString("name");
        String message = args.getString("message");
        String type = args.getString("type");
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(message)) {
            session.sendText(JsonResponse.JsonStringResponse(new MsgMessage("发送失败!")));
        }
        PlayerCharacterManager.sendMessageToOtherPlayer(caller, name, message, session,type);
    }
}
