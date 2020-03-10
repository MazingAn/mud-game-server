package com.mud.game.commands.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.commands.BaseCommand;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;


/**
 * 获取当前位置的技能信息
 * 请求示例:
 * <pre>
 *    {
 *        "cmd": "skill_by_position",
 *        "args": "position" //技能装备位置
 *    }
 * </pre>
 * */

public class GetSkillsByPosition extends BaseCommand {
    /*
    * @ 根据技能装备的位置获取玩家的技能（分为已装备和可替换两组）
    * @ {"cmd": "skill_by_position", "args": "position"}
    * */

    public GetSkillsByPosition(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException, JsonProcessingException {
        PlayerCharacter caller = (PlayerCharacter)getCaller();
        Session session = getSession();
        String position = getArgs().getString("args");
        PlayerCharacterManager.getSkillsByPosition(caller, position, session);
    }
}
