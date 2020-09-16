package com.mud.game.commands.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.MsgMessage;
import com.mud.game.messages.ToastMessage;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.manager.HangUpManager;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.manager.PlayerScheduleManager;
import com.mud.game.object.typeclass.EquipmentObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.structs.CharacterState;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.utils.resultutils.GameWords;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * 玩家开始挖矿
 * <p>
 * 请求示例：
 * <pre>
 *     {
 *         "cmd": "mining",
 *         "args": "" // args 可以为空
 *     }
 * </pre>
 */
public class HangUpMining extends BaseCommand {

    public HangUpMining(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        Session session = getSession();
        // 检查玩家有没有挖矿技能
        SkillObject skillObject = GameCharacterManager.getSkill(caller, "skill_zhishi_wakuang");
        EquipmentObject equipmentObject = PlayerCharacterManager.getPositionLeftHand(caller, "OBJECT_TIEGAO");
        if (null == skillObject) {
            session.sendText(JsonResponse.JsonStringResponse(new ToastMessage(GameWords.NO_MINING_SKILL)));
        } else if (null == equipmentObject) {
            session.sendText(JsonResponse.JsonStringResponse(new ToastMessage(GameWords.NO_MINING_EQUIPMENT)));
        } else {
            //触发概率加成
            //TODO 根据技能等级/装备品级决定触发频率
            Float addProbability = HangUpManager.getAddProbability(skillObject.getLevel(), equipmentObject.getQuality());
            Runnable runnable = HangUpManager.start(caller, CharacterState.STATE_MINING, addProbability);
            if (runnable != null) {
                ScheduledExecutorService service = PlayerScheduleManager.createOrGetExecutorServiceForCaller(caller.getId());
                service.scheduleAtFixedRate(runnable, 0, 3000, TimeUnit.MILLISECONDS);
            }
        }

    }
}
