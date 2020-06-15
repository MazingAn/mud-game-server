package com.mud.game.commands.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.FriendListMessage;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.utils.jsonutils.JsonResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;


/**
 *  加载玩家的好友列表
 * */

public class LoadFriends extends BaseCommand {

    public LoadFriends(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        PlayerCharacter playerCharacter = (PlayerCharacter) getCaller();
        Session session = getSession();
        session.sendText(JsonResponse.JsonStringResponse(new FriendListMessage(playerCharacter)));
    }
}
