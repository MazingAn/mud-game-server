package com.mud.game.object.manager;

import com.mongodb.Mongo;
import com.mud.game.handler.ConditionHandler;
import com.mud.game.handler.QuestStatusHandler;
import com.mud.game.object.account.Player;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.QuestObject;
import com.mud.game.structs.ObjectiveShowStatus;
import com.mud.game.structs.ObjectiveStatus;
import com.mud.game.utils.resultutils.GameWords;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.Quest;
import com.mud.game.worlddata.db.models.QuestObjective;
import com.mud.game.worlddata.db.models.QuestRewardList;
import com.mud.game.worldrun.db.mappings.MongoMapper;

import java.util.*;

public class GameQuestManager {


    /**
     * 创建任务
     * @param playerCharacter 玩家
     * @param questKey 任务key
     * @return QuestObject 任务实例
     * */
    public static QuestObject create(PlayerCharacter playerCharacter, String questKey){
        QuestObject questObject = null;
        try{
            if (canAcceptQuest(playerCharacter, questKey)) {
                Quest template = DbMapper.questRepository.findQuestByDataKey(questKey);
                questObject = new QuestObject();
                questObject.setDataKey(questKey);
                questObject.setName(template.getName());
                questObject.setDescription(template.getDescription());
                questObject.setQuestType(template.getQuestType());
                questObject.setOwner(playerCharacter.getId());
                questObject.setStatus(QuestStatusHandler.NOT_ACCOMPLISHED);
                questObject.setQuestType(template.getQuestType());
                // 设置任务目标
                bindQuestObjectives(questObject, questKey);
                // 设置任务奖励
                bindQuestRewards(questObject, questKey);
                MongoMapper.questObjectRepository.save(questObject);
            }
        }catch (Exception e){
            questObject = null;
            playerCharacter.msg(GameWords.CANNOT_CREATE_QUEST);
            e.printStackTrace();
        }
        return questObject;
    }

    /**
     * 绑定任务目标
     * @param questKey 任务key
     * */
    public static void bindQuestObjectives(QuestObject questObject, String questKey){
        Iterable<QuestObjective> questObjectives = DbMapper.questObjectiveRepository.findQuestObjectivesByQuest(questKey);
        Set<ObjectiveStatus> objectives = new HashSet<>();
        for(QuestObjective questObjective : questObjectives){
            objectives.add(new ObjectiveStatus(questObjective));
        }
        questObject.setObjectives(objectives);
    }

    /**
     * 绑定任务奖励
     * @param questKey 任务key
     * */
    public static void bindQuestRewards(QuestObject questObject, String questKey){
        Iterable<QuestRewardList> questRewardLists = DbMapper.questRewardListRepository.findQuestRewardListsByProvider(questKey);
        Set<QuestRewardList> rewards = new HashSet<>();
        for(QuestRewardList questRewardList : questRewardLists){
            rewards.add(questRewardList);
        }
        questObject.setRewards(rewards);
    }


    /**
     * 检查玩家是否正在执行某个任务
     * @param playerCharacter  玩家
     * @param questKey 任务key
     * @return boolean 是否正在执行某个任务
     * */
    public static boolean isQuestInProgress(PlayerCharacter playerCharacter, String questKey){
        Set<String> currentQuests =  playerCharacter.getCurrentQuests();
        for(String questId : currentQuests){
            QuestObject questObject = MongoMapper.questObjectRepository.findQuestObjectById(questId);
            if(questObject.getDataKey().equals(questKey) && !isQuestAccomplished(playerCharacter, questKey)){
                return true;
            }
        }
        return false;
    }


    /**
     * 检查玩家收否能够接受任务
     * @param playerCharacter 玩家
     * @param questKey 任务key
     * @return boolean 能够接受任务
     * */
    public static boolean canAcceptQuest(PlayerCharacter playerCharacter, String questKey){
        for(String questId : playerCharacter.getFinishedQuests()){
            QuestObject questObject = MongoMapper.questObjectRepository.findQuestObjectById(questId);
            if(questObject.getDataKey().equals(questKey)){
                return false;
            }
        }
        Quest quest = DbMapper.questRepository.findQuestByDataKey(questKey);
        String actionCondition = quest.getActionCondition();
        boolean match = false;
        if(actionCondition == null || actionCondition.equals("")){
            match = true;
        }else{
            match = ConditionHandler.matchCondition(actionCondition, playerCharacter);
        }
        return match && !isQuestInProgress(playerCharacter, questKey) && !isQuestAccomplished(playerCharacter,questKey);
    }

    /**
     * 检查某个任务是否已经完成
     * @param playerCharacter 玩家
     * @param questKey 玩家的任务Key
     * */
    public static boolean isQuestAccomplished(PlayerCharacter playerCharacter, String questKey) {
        QuestObject questObject = null;
        for(String questId : playerCharacter.getCurrentQuests()){
            QuestObject tmp = MongoMapper.questObjectRepository.findQuestObjectById(questId);
            if(tmp.getDataKey().equals(questKey)){
                questObject = tmp;
                break;
            }
        }

        if(questObject == null){
            return false;
        }

        boolean result = true;
        for (ObjectiveStatus objectiveStatus : questObject.getObjectives()) {
            result = objectiveStatus.getTotal() <= objectiveStatus.getAccomplished();
        }
        return result;
    }


    public static List<ObjectiveShowStatus> returnObjectives(PlayerCharacter playerCharacter, QuestObject questObject){
        List<ObjectiveShowStatus> objectiveShowStatuses = new ArrayList<>();
        for(ObjectiveStatus objectiveStatus : questObject.getObjectives()){
            objectiveShowStatuses.add(new ObjectiveShowStatus(objectiveStatus));
        }
        return objectiveShowStatuses;
    }

}
