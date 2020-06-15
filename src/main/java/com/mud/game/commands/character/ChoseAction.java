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

/**
 * 玩家出发了一个物体的action事件
 *
 *
 * 请求示例：
 * <pre>
 * {
 *      cmd: "chose_action",
 *      args: {
 *          dbref: "5e6702e8c94709756f1dd1d5", //对应物体的id
 *          event: "event_data_87b9f068-58de-4f81-8237-5aa1cd94cb72" //对应事件的id
 *      }
 * }
 * </pre>
 *
 * 关于事件请参考： {@link com.mud.game.events.EventAction}
 *
 * */

public class ChoseAction extends BaseCommand {

    public ChoseAction(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        PlayerCharacter playerCharacter = (PlayerCharacter) getCaller();
        Session session = getSession();
        JSONObject args = getArgs();
        String eventKey = args.getString("event");
        EventData eventData = DbMapper.eventDataRepository.findEventDataByDataKey(eventKey);
        GameEventManager.trigger(playerCharacter, eventData);
    }
}
