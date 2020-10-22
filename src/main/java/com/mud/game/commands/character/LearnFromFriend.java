package com.mud.game.commands.character;

import com.mud.game.combat.CombatSense;
import com.mud.game.combat.NormalCombat;
import com.mud.game.commands.BaseCommand;
import com.mud.game.handler.CombatHandler;
import com.mud.game.handler.GraduationHandler;
import com.mud.game.messages.ToastMessage;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.util.ArrayList;

import static com.mud.game.constant.Constant.CONTEST_MIN_HP_COEFFICIENT;

/**
 * 和玩家切磋
 * <p>
 * cmd:learn_from_friend
 * args : 'player.s id' //玩家id
 */
public class LearnFromFriend extends BaseCommand {
    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public LearnFromFriend(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        CombatSense combatSense = CombatHandler.getCombatSense(caller.getId());
        JSONObject args = getArgs();
        String target = args.getString("args");
        CommonCharacter targetObject = MongoMapper.playerCharacterRepository.findPlayerCharacterById(target);
        if (caller.getHp() <= caller.getMax_hp() * CONTEST_MIN_HP_COEFFICIENT) {
            caller.msg(new ToastMessage("{r你的血量过低，不能切磋！{g"));
            return;
        }

        if (targetObject.getHp() <= targetObject.getMax_hp() * CONTEST_MIN_HP_COEFFICIENT) {
            caller.msg( new ToastMessage("{r" +targetObject.getName() + "的血量过低，不能切磋！{g"));
            return;
        }
        if (combatSense == null) {
            ArrayList<CommonCharacter> redTeam = new ArrayList<>();
            ArrayList<CommonCharacter> blueTeam = new ArrayList<>();
            redTeam.add(caller);
            blueTeam.add(targetObject);
            //设置结束生命值 minHp=-1 是结束战斗血量下限为当前目标的百分比
            combatSense = new CombatSense(redTeam, blueTeam, -1);
        } else {
            combatSense.getBlueTeam().add(targetObject);
        }
        CombatHandler.addCombatSense(targetObject.getId(), combatSense);
        CombatHandler.addCombatSense(caller.getId(), combatSense);
        //记录切磋
        GraduationHandler.addGraduationList(caller.getId() + targetObject.getId());
        GraduationHandler.addGraduationList(targetObject.getId() + caller.getId());
        //开启自动攻击
        NormalCombat normalCombat = new NormalCombat();
        normalCombat.init(combatSense);
        normalCombat.startCombat(combatSense);
    }
}
