package com.mud.game.commands.character;

import com.mud.game.combat.CombatSense;
import com.mud.game.combat.NormalCombat;
import com.mud.game.commands.BaseCommand;
import com.mud.game.handler.CombatHandler;
import com.mud.game.handler.NpcCombatHandler;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.WorldNpcObject;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.util.ArrayList;

/**
 * 开始攻击某一个对手（玩家或者NPC）
 * <p>
 * 请求示例：
 * <pre>
 * {
 *      "cmd": "attack",
 *      "args": "攻击对象的ID"
 * }
 * </pre>
 */

public class Attack extends BaseCommand {


    public Attack(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        JSONObject args = getArgs();
        String target = args.getString("args");
        CommonCharacter targetObject = null;
        if (MongoMapper.worldNpcObjectRepository.existsById(target)) {
            targetObject = MongoMapper.worldNpcObjectRepository.findWorldNpcObjectById(target);
        } else {
            targetObject = MongoMapper.playerCharacterRepository.findPlayerCharacterById(target);
        }
        CombatSense combatSense = CombatHandler.getCombatSense(caller.getId());
        if (combatSense == null) {
            ArrayList<CommonCharacter> redTeam = new ArrayList<>();
            ArrayList<CommonCharacter> blueTeam = new ArrayList<>();
            redTeam.add(caller);
            blueTeam.add(targetObject);
            combatSense = new CombatSense(redTeam, blueTeam, 0);
        } else {
            combatSense.getBlueTeam().add(targetObject);
        }

        // 如果攻击对象是npc的话，保存在NpcCombatHandler,否则保存在CombatHandler
        if (targetObject instanceof WorldNpcObject) {
            NpcCombatHandler.addNpcCombatSense(targetObject.getId(), caller.getId(), combatSense);
        } else {
            CombatHandler.addCombatSense(targetObject.getId(), combatSense);
        }
        CombatHandler.addCombatSense(caller.getId(), combatSense);

        NormalCombat normalCombat = new NormalCombat();
        normalCombat.init(combatSense);
        normalCombat.startCombat(combatSense);
    }

}
