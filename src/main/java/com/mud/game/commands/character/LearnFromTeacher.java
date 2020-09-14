package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.object.manager.WorldNpcObjectManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.WorldNpcObject;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;


/**
 *  玩家在师傅那里学习技能 返回是否可以教授的技能列表
 * 请求示例：
 * <pre>
 *      {
 *          "cmd":  "learn_from_teacher",
 *          "args": "5e0b2979b5de8a0a2787d9d1" //教授技能的人的ID
 *      }
 * </pre>
 * */
public class LearnFromTeacher extends BaseCommand {

    public LearnFromTeacher(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        Session session = getSession();
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        JSONObject args = getArgs();
        String teacherId = args.getString("args");
        WorldNpcObject teacher = MongoMapper.worldNpcObjectRepository.findWorldNpcObjectById(teacherId);
        WorldNpcObjectManager.getCanTeachSkills(teacher, caller, session);
    }
}
