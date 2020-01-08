package com.mud.game.commands.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.commands.BaseCommand;
import com.mud.game.object.account.Player;
import com.mud.game.object.manager.WorldObjectCreatorManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.WorldObjectCreator;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

public class Loot extends BaseCommand {
    public Loot(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException, JsonProcessingException {
        JSONObject args = getArgs();
        Session session = getSession();
        PlayerCharacter playerCharacter = (PlayerCharacter) getCaller();
        String creatorId = args.getString("args");
        WorldObjectCreator creator = MongoMapper.worldObjectCreatorRepository.findWorldObjectCreatorById(creatorId);
        WorldObjectCreatorManager.onPlayerLoot(creator, playerCharacter, session);
    }
}
