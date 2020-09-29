package com.mud.game.commands.character;

import com.mud.game.combat.CombatSense;
import com.mud.game.combat.NormalCombat;
import com.mud.game.commands.BaseCommand;
import com.mud.game.handler.AutoContestHandler;
import com.mud.game.handler.CombatHandler;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 玩家向师傅发起请教
 * <p>
 * 赢了师傅可以出师
 * 双方血量需要低于10%战斗结束 、
 * <p>
 * {"cmd":"left_teacher",
 * "args":<shop's key>
 * }
 */
public class LeftTeacher extends BaseCommand {
    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public LeftTeacher(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        //请教师傅必须满状态
        JSONObject args = getArgs();
        Session session = getSession();
        PlayerCharacter playerCharacter = (PlayerCharacter) getCaller();
        //npc师傅的id
        String target = args.getString("args");
        CommonCharacter targetObject = MongoMapper.worldNpcObjectRepository.findWorldNpcObjectById(target);
        if (null == targetObject) {
            return;
        }
        //初始化npc状态
        targetObject.setHp(targetObject.getMax_hp());
        targetObject.setMp(targetObject.getMax_mp());
        targetObject.setBuffers(new HashMap<String, Set<String>>());
        ArrayList<CommonCharacter> redTeam = new ArrayList<>();
        ArrayList<CommonCharacter> blueTeam = new ArrayList<>();
        redTeam.add(playerCharacter);
        blueTeam.add(targetObject);
        CombatSense combatSense = new CombatSense(redTeam, blueTeam, 0);
        CombatHandler.addCombatSense(targetObject.getId() + playerCharacter.getId(), combatSense);
        CombatHandler.addCombatSense(playerCharacter.getId() + targetObject.getId(), combatSense);
        AutoContestHandler.addCommonCharacter(targetObject.getId() + playerCharacter.getId(), targetObject);
        NormalCombat normalCombat = new NormalCombat();
        normalCombat.init(combatSense);
        normalCombat.startContest(playerCharacter, targetObject, combatSense);
    }
}
