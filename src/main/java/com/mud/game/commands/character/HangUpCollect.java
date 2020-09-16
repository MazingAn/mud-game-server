package com.mud.game.commands.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.EquipmentMessage;
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
 * 玩家开始采药
 * <p>
 * 请求示例：
 * <pre>
 *     {
 *         "cmd": "collection",
 *         "args": "" // args 可以为空
 *     }
 * </pre>
 */
public class HangUpCollect extends BaseCommand {

    public HangUpCollect(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        Session session = getSession();
        SkillObject skillObject = GameCharacterManager.getSkill(caller, "skill_zhishi_caiyao");
        EquipmentObject equipmentObject = PlayerCharacterManager.getPositionLeftHand(caller, "OBJECT_YAOLIAN");
        // 检查玩家有没有采药技能
        if (null == skillObject) {
            session.sendText(JsonResponse.JsonStringResponse(new ToastMessage(GameWords.NO_COLLECT_SKILL)));
        } else if (null == equipmentObject) {
            session.sendText(JsonResponse.JsonStringResponse(new ToastMessage(GameWords.NO_COLLECT_EQUIPMENT)));
        } else {
            //触发概率加成
            //TODO 根据技能等级/装备品级决定触发频率
            Float addProbability = HangUpManager.getAddProbability(skillObject.getLevel(), equipmentObject.getQuality());
            Runnable runnable = HangUpManager.start(caller, CharacterState.STATE_COLLECT,addProbability);
            if (runnable != null) {
                ScheduledExecutorService service = PlayerScheduleManager.createOrGetExecutorServiceForCaller(caller.getId());
                service.scheduleAtFixedRate(runnable, 0, 3000, TimeUnit.MILLISECONDS);
            }
        }

    }
}
