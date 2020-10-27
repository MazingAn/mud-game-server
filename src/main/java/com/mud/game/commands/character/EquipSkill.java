package com.mud.game.commands.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.commands.BaseCommand;
import com.mud.game.object.account.Player;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.manager.SkillObjectManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

/**
 * 玩家装备一个技能
 * 请求示例:
 * <pre>
 *    {
 *        "cmd": "equip_skill",
 *        "args": {
 *           "dbref": "skill_id",
 *           "position": "position"
 *        }
 *    }
 * </pre>
 */
public class EquipSkill extends BaseCommand {

    public EquipSkill(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        /*
         * 玩家装备技能到身上
         * */
        Session session = getSession();
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        JSONObject args = getArgs();
        String skillId = args.getString("dbref");
        String position = args.getString("position");
        SkillObject skillObject = MongoMapper.skillObjectRepository.findSkillObjectById(skillId);
        SkillObjectManager.equipTo(skillObject, caller, position, session, false);
    }
}
