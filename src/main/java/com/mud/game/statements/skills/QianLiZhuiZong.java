package com.mud.game.statements.skills;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.messages.MsgMessage;
import com.mud.game.messages.ToastMessage;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.object.typeclass.WorldRoomObject;
import com.mud.game.statements.BaseAttackSkillStatement;
import com.mud.game.utils.StateConstants;
import com.mud.game.utils.resultutils.GameWords;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;

import static com.mud.game.utils.StateConstants.CHECK_GOTO_ROOM_STATE;

public class QianLiZhuiZong extends BaseAttackSkillStatement {
    /**
     * 攻击技能基类的构造函数
     *
     * @param caller      调用者
     * @param target      受攻击者
     * @param skillObject 使用的技能
     * @param key         技能函数的名称
     * @param args        技能函数的参数列表
     */
    public QianLiZhuiZong(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, String key, String[] args) {
        super(caller, target, skillObject, key, args);
    }

    @Override
    public void attack() throws JSONException, JsonProcessingException {
        //基本参数
        CommonCharacter caller = getCaller();
        CommonCharacter target = getTarget();

        String name = "";

        CommonCharacter commonCharacter = null;

        //判断状态
        // 玩家是否死亡
        if (StateConstants.checkState(caller, CHECK_GOTO_ROOM_STATE)) {
            if (MongoMapper.playerCharacterRepository.existsByName(name)) {
                commonCharacter = MongoMapper.playerCharacterRepository.findPlayerCharacterByName(name);
            } else {
                commonCharacter = MongoMapper.worldNpcObjectRepository.findWorldNpcObjectByName(name);
            }
            if (commonCharacter == null) {
                //TODO 提示
            }
            WorldRoomObject roomObject = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectById(commonCharacter.getLocation());
            PlayerCharacterManager.moveTo((PlayerCharacter) caller, roomObject.getDataKey());
        } else {
            caller.msg(new ToastMessage("当前状态不可移动！"));
        }
    }
}
