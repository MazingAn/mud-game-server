package com.mud.game.commands.character;

import com.mud.game.combat.CombatSense;
import com.mud.game.commands.BaseCommand;
import com.mud.game.handler.CombatHandler;
import com.mud.game.messages.ToastMessage;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import static com.mud.game.constant.PostConstructConstant.*;

/**
 * 减少犯罪值
 */
public class CutBackCrimevalue extends BaseCommand {
    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public CutBackCrimevalue(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        JSONObject args = getArgs();

        // 减少量
        int value = 1;
        // 需要的金币
        int money = 1;
        caller = MongoMapper.playerCharacterRepository.findPlayerCharacterById(caller.getId());
        if (caller.getCrimeValue() == 0) {
            //犯罪值为0
            caller.msg(new ToastMessage(CUT_BACKCRIME_ZERO_NPC));
            return;
        }
        if (PlayerCharacterManager.castMoney(caller, "OBJECT_JINZI", money)) {
            //减去犯罪值
            int newCrimeValue = caller.getCrimeValue() - value > 0 ? caller.getCrimeValue() - value : 0;
            int cutValue = caller.getCrimeValue() - newCrimeValue;
            caller.setCrimeValue(newCrimeValue);
            MongoMapper.playerCharacterRepository.save(caller);
            caller.msg(new ToastMessage(String.format(CUT_BACKCRIME_ATTACK, cutValue)));
        } else {
            //你的钱不够
            caller.msg(new ToastMessage("你的钱不够！"));
        }
    }
}
