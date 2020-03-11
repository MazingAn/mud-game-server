package com.mud.game.commands.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.MsgMessage;
import com.mud.game.object.manager.PlayerScheduleManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.utils.resultutils.GameWords;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;


/**
 * 玩家停止挂机活动
 *
 * 请求示例：
 * <pre>
 *     {
 *         "cmd": "stop",
 *         "args": "" // args 可以为空
 *     }
 * </pre>
 *
 * */

public class HangUpStop extends BaseCommand {
    /*
    * @ 玩家停止挂机
    * */

    public HangUpStop(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        Session session = getSession();
        PlayerScheduleManager.shutdownExecutorByCallerId(caller.getId());
        switch (caller.getState()){
            case STATE_MINING:
                session.sendText(JsonResponse.JsonStringResponse(new MsgMessage(GameWords.STOP_MINING)));
                break;
            case STATE_COLLECT:
                session.sendText(JsonResponse.JsonStringResponse(new MsgMessage(GameWords.STOP_COLLECT)));
                break;
            case STATE_CURE:
                session.sendText(JsonResponse.JsonStringResponse(new MsgMessage(GameWords.STOP_CURE)));
                break;
            case STATE_FISHING:
                session.sendText(JsonResponse.JsonStringResponse(new MsgMessage(GameWords.STOP_FISHING)));
                break;
            case STATE_MEDITATE:
                session.sendText(JsonResponse.JsonStringResponse(new MsgMessage(GameWords.STOP_MEDITATE)));
                break;
            case STATE_LEARN_SKILL:
                session.sendText(JsonResponse.JsonStringResponse(new MsgMessage(GameWords.STOP_LEARN_SKILL)));
                break;
            default:
                break;
        }
    }
}
