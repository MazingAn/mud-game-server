package com.mud.game.object.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.events.EventAction;
import com.mud.game.events.EventActionType;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.*;
import org.json.JSONException;
import org.yeauty.pojo.Session;

public class GameEventManager {
    /*
    * 游戏事件的选择和执行管理
    * */
    public static void trigger(PlayerCharacter playerCharacter, EventData eventData, Session session) throws JsonProcessingException, JSONException {
        /*
        * 根据事件的类型 执行对应事件
        * */

        switch (eventData.getAction()){
            case EventActionType.ACTION_ATTACK:
                ActionAttack attackData = DbMapper.actionAttackRepository.findActionAttackByEventKey(eventData.getDataKey());
                EventAction.actionAttack(playerCharacter, attackData, session);
                break;
            case EventActionType.ACTION_ACCEPT_QUEST:
                ActionAcceptQuest acceptQuest = DbMapper.actionAcceptQuestRepository.findActionAcceptQuestByEventKey(eventData.getDataKey());
                EventAction.actionAcceptQuest(playerCharacter, acceptQuest, session);
                break;
            case EventActionType.ACTION_CHANGE_ATTR:
                ActionChangeAttr changeAttr = DbMapper.actionChangeAttrRepository.findActionChangeAttrByEventKey(eventData.getDataKey());
                EventAction.actionChangeAttr(playerCharacter, changeAttr, session);
                break;
            case EventActionType.ACTION_CHANGE_STEP:
                ActionChangeStep changeStep = DbMapper.actionChangeStepRepository.findActionChangeStepsByEventKey(eventData.getDataKey());
                EventAction.actionChangeStep(playerCharacter, changeStep, session);
                break;
            case EventActionType.ACTION_CLOSE:
                ActionCloseEvent closeEvent = DbMapper.actionCloseEventRepository.findActionCloseEventByEventKey(eventData.getDataKey());
                EventAction.actionCloseEvent(playerCharacter, closeEvent, session);
                break;
            case EventActionType.ACTION_DIALOGUE:
                ActionDialogue actionDialogue = DbMapper.actionDialogueRepository.findActionDialogueByEventKey(eventData.getDataKey());
                EventAction.actionDialogue(playerCharacter, actionDialogue, session);
                break;
            case EventActionType.ACTION_LEARN_SKILL:
                ActionLearnSkill learnSkill = DbMapper.actionLearnSkillRepository.findActionLearnSkillByEventKey(eventData.getDataKey());
                EventAction.actionLearnSkill(playerCharacter, learnSkill, session);
                break;
            case EventActionType.ACTION_MOVE:
                ActionMove move = DbMapper.actionMoveRepository.findActionMoveByEventKey(eventData.getDataKey());
                EventAction.actionMove(playerCharacter, move, session);
                break;
            case EventActionType.ACTION_TURN_IN_QUEST:
                ActionTurnInQuest turnInQuest = DbMapper.actionTurnInQuestRepository.findActionTurnInQuestByEventKey(eventData.getDataKey());
                EventAction.actionTurnInQuest(playerCharacter, turnInQuest, session);
                break;
            default:
                break;
        }

    }


}
