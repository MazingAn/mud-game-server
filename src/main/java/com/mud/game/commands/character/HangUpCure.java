package com.mud.game.commands.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.PlayerCharacterStateMessage;
import com.mud.game.object.manager.HangUpManager;
import com.mud.game.object.manager.PlayerScheduleManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.structs.PlayerCharacterState;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HangUpCure extends BaseCommand {

    /*
    * @玩家开始疗伤
    * */

    public HangUpCure(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException, JsonProcessingException {
        PlayerCharacter caller = (PlayerCharacter)getCaller();
        Session session = getSession();
        Runnable runnable = HangUpManager.start(caller, PlayerCharacterState.STATE_CURE, session);
        if(runnable != null){
            ScheduledExecutorService service = PlayerScheduleManager.createOrGetExecutorServiceForCaller(caller.getId());
            service.scheduleAtFixedRate(runnable, 0, 3000, TimeUnit.MILLISECONDS);
            caller.setState(PlayerCharacterState.STATE_CURE);
            MongoMapper.playerCharacterRepository.save(caller);
            session.sendText(JsonResponse.JsonStringResponse(new PlayerCharacterStateMessage(caller.getState())));
        }
    }
}
