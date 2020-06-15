package com.mud.game.commands.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.PlayerCharacterStateMessage;
import com.mud.game.object.manager.HangUpManager;
import com.mud.game.object.manager.PlayerScheduleManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.structs.CharacterState;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * 玩家开始疗伤
 *
 * 请求示例：
 * <pre>
 *     {
 *         "cmd": "cure",
 *         "args": "" // args 可以为空
 *     }
 * </pre>
 *
 * */
public class HangUpCure extends BaseCommand {

    public HangUpCure(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        PlayerCharacter caller = (PlayerCharacter)getCaller();
        // 开始疗伤挂机
        Runnable runnable = HangUpManager.start(caller, CharacterState.STATE_CURE);
        if(runnable != null){
            ScheduledExecutorService service = PlayerScheduleManager.createOrGetExecutorServiceForCaller(caller.getId());
            service.scheduleAtFixedRate(runnable, 0, 3000, TimeUnit.MILLISECONDS);
        }
    }
}
