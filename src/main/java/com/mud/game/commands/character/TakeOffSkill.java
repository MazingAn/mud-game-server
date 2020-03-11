package com.mud.game.commands.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.commands.BaseCommand;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.manager.SkillObjectManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;


/**
 * 玩家取消装备一个技能
 * Usage:
 * <pre>
 *    {
 *        "cmd": "take_off_skill",
 *        "args": {
 *           "dbref": "skill_id", //技能ID
 *           "position": "position" //要卸掉技能的位置
 *        }
 *    }
 *  </pre>
 * */

public class TakeOffSkill extends BaseCommand {

    public TakeOffSkill(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        /*
        * 玩家取消装备技能
        * */
        Session session = getSession();
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        JSONObject args = getArgs();
        String skillId = args.getString("dbref");
        String position = args.getString("position");
        SkillObject skillObject = MongoMapper.skillObjectRepository.findSkillObjectById(skillId);
        SkillObjectManager.takeOff(skillObject, caller, position, session);
    }
}
