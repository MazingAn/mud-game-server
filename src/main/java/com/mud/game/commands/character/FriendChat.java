package com.mud.game.commands.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.commands.BaseCommand;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

public class FriendChat extends BaseCommand {

    public FriendChat(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException, JsonProcessingException {
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        Session session = getSession();
        JSONObject args = getArgs();
        String targetId = args.getString("dbref");
        String message = args.getString("message");
        PlayerCharacterManager.sendMessageToOtherPlayer(caller, targetId, message, session);
    }
}
