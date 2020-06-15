package com.mud.game.commands.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.MsgMessage;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.manager.HangUpManager;
import com.mud.game.object.manager.PlayerScheduleManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.structs.CharacterState;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.utils.resultutils.GameWords;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * 玩家开始挖矿
 *
 * 请求示例：
 * <pre>
 *     {
 *         "cmd": "mining",
 *         "args": "" // args 可以为空
 *     }
 * </pre>
 *
 * */
public class HangUpMining extends BaseCommand {

    public HangUpMining(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        PlayerCharacter caller = (PlayerCharacter)getCaller();
        Session session = getSession();
        // 检查玩家有没有挖矿技能
        if(!GameCharacterManager.hasSkill(caller, "skill_zhishi_wakuang")){
            session.sendText(JsonResponse.JsonStringResponse(new MsgMessage(GameWords.NO_MINING_SKILL)));
        }else{
            Runnable runnable = HangUpManager.start(caller, CharacterState.STATE_MINING);
            if(runnable != null){
                ScheduledExecutorService service = PlayerScheduleManager.createOrGetExecutorServiceForCaller(caller.getId());
                service.scheduleAtFixedRate(runnable, 0, 3000, TimeUnit.MILLISECONDS);
            }
        }

    }
}
