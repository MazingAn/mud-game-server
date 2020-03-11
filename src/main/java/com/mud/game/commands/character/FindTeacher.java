package com.mud.game.commands.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.commands.BaseCommand;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

/**
 *  玩家寻找一个师傅（拜师）
 *
 *  使用示例：
 *  <pre>
 *      {
 *          "cmd": find_teacher,
 *          args : "目标的id（dbref）"
 *      }
 *  </pre>
 * */

public class FindTeacher extends BaseCommand {


    public FindTeacher(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        Session session = getSession();
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        JSONObject args = getArgs();
        String teacherId = args.getString("args");
        PlayerCharacterManager.findTeacher(caller, teacherId, session);
    }

}
