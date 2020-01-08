package com.mud.game.commands.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.commands.BaseCommand;
import com.mud.game.object.manager.GameEventManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.EventData;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

public class ChoseAction extends BaseCommand {

    public ChoseAction(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException, JsonProcessingException {
        PlayerCharacter playerCharacter = (PlayerCharacter) getCaller();
        Session session = getSession();
        JSONObject args = getArgs();
        String eventKey = args.getString("event");
        EventData eventData = DbMapper.eventDataRepository.findEventDataByDataKey(eventKey);
        GameEventManager.trigger(playerCharacter, eventData, session);
    }
}
