package com.mud.game.commands.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.combat.CombatSense;
import com.mud.game.combat.NormalCombat;
import com.mud.game.commands.BaseCommand;
import com.mud.game.handler.CombatHandler;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.worlddata.db.models.NormalObject;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.util.ArrayList;

/**
 * 开始攻击某一个对手（玩家或者NPC）
 *
 * 请求示例：
 * <pre>
 * {
 *      "cmd": "attack",
 *      "args": "攻击对象的ID"
 * }
 * </pre>
 *
 * */

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
        if(MongoMapper.worldNpcObjectRepository.existsById(target)){
            targetObject = MongoMapper.worldNpcObjectRepository.findWorldNpcObjectById(target);
        }else{
            targetObject = MongoMapper.playerCharacterRepository.findPlayerCharacterById(target);
        }

        ArrayList<CommonCharacter> redTeam = new ArrayList<>();
        ArrayList<CommonCharacter> blueTeam = new ArrayList<>();
        redTeam.add(caller);
        blueTeam.add(targetObject);

        CombatSense combatSense = new CombatSense(redTeam, blueTeam, 0);
        CombatHandler.addCombatSense(targetObject.getId(), combatSense);
        CombatHandler.addCombatSense(caller.getId(), combatSense);

        NormalCombat normalCombat = new NormalCombat();
        normalCombat.init(combatSense);
        normalCombat.startCombat(combatSense );
    }
}
