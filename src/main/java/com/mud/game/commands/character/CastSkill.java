package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.object.account.Player;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

/**
 * 玩家执行技能
 * <pre>
 *  {
 *      "cmd":"castskill",
 *      "args":
 *      {
 *          "skill": "技能key",
 *          "target":"目标ID",
 *          "combat": true
 *      }
 *  }"
 *
 * </pre>
 * */
public class CastSkill extends BaseCommand {
    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public CastSkill(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        JSONObject args = getArgs();
        String skillKey = args.getString("skill");
        String targetId = args.getString("target");
        boolean combat = args.getBoolean("combat");
        CommonCharacter target = GameCharacterManager.getCharacterObject(targetId);
        SkillObject skillObject = GameCharacterManager.getCharacterSkillByDataKey(caller, skillKey);
        GameCharacterManager.castSkill(caller, target, skillObject);
    }
}
