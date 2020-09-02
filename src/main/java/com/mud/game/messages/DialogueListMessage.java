package com.mud.game.messages;

import com.mud.game.handler.QuestStatusHandler;
import com.mud.game.object.manager.GameEventManager;
import com.mud.game.object.manager.GameQuestManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.WorldNpcObject;
import com.mud.game.structs.Sentence;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.DialogueSentence;
import com.mud.game.worlddata.db.models.EventData;
import com.mud.game.worlddata.db.models.NpcDialogue;
import com.mud.game.worlddata.db.models.QuestDialogueDependency;

import java.util.ArrayList;
import java.util.List;

public class DialogueListMessage {
    private List<Sentence> dialogues_list;

    /**
     * 检查玩家和NPC之间的对话
     * 1.先检查NPC有没有任务可与给玩家
     * 2.如果有任务交给玩家则把玩家任务信息加入对话列表
     * 3.在检查玩家有没有任务可以提交给NPC
     * 4.如果有任务可以提交则把玩家提交任务信息的对话加入对话列表
     * 5.在检查玩家没有完成任务的时候NPC有没有什么想说的
     * 6.如果又想说的就把对应的对话加到对话列表
     * 7.如果上述对话都没有触发 则显示一个NPC的默认对话
     * */
    public DialogueListMessage(PlayerCharacter playerCharacter, WorldNpcObject npc) {
        dialogues_list = new ArrayList<>();

        // 遍历所有npc的对话
        for(String dialogueKey: npc.getDialogues()){
            //遍历当前对话有关的任务依赖
            Iterable <QuestDialogueDependency> questDialogueDependencies = DbMapper.questDialogueDependencyRepository.findQuestDialogueDependenciesByDialogue(dialogueKey);
            for(QuestDialogueDependency questDialogueDependency : questDialogueDependencies){
                // 如果当前对话显示可以获取任务
                if(questDialogueDependency.getType().equals(QuestStatusHandler.CAN_PROVIDE)){
                    // 如果玩家刚好满足接受这个任务的条件
                    if(GameQuestManager.canAcceptQuest(playerCharacter, questDialogueDependency.getDependency())){
                        // 获得对话信息 把第一句加入对话列表
                        Iterable<DialogueSentence> dialogueSentences = DbMapper.dialogueSentenceRepository.findDialogueSentencesByDialogueOrderByOrdinalAsc(questDialogueDependency.getDialogue());
                        dialogues_list.add(new Sentence(dialogueSentences.iterator().next(), playerCharacter, npc));
                    }
                }
                // 如果当前对话关联了的任务进行中
                if(questDialogueDependency.getType().equals(QuestStatusHandler.NOT_ACCOMPLISHED)){
                    // 并且玩家对应的任务，也刚好是在未完成状态
                    if(GameQuestManager.isQuestInProgress(playerCharacter, questDialogueDependency.getDependency())){
                        // 获得对话信息 把第一句加入对话列表
                        Iterable<DialogueSentence> dialogueSentences = DbMapper.dialogueSentenceRepository.findDialogueSentencesByDialogueOrderByOrdinalAsc(questDialogueDependency.getDialogue());
                        dialogues_list.add(new Sentence(dialogueSentences.iterator().next(), playerCharacter, npc));
                    }
                }
                // 如果当前的对话关联了任务结束
                if(questDialogueDependency.getType().equals(QuestStatusHandler.ACCOMPLISHED)){
                    //而且玩家对应的任务也刚还是结束状态
                    if(GameQuestManager.isQuestAccomplished(playerCharacter, questDialogueDependency.getDependency())){
                        // 获得对话信息 把第一句加入对话列表
                        Iterable<DialogueSentence> dialogueSentences = DbMapper.dialogueSentenceRepository.findDialogueSentencesByDialogueOrderByOrdinalAsc(questDialogueDependency.getDialogue());
                        dialogues_list.add(new Sentence(dialogueSentences.iterator().next(), playerCharacter, npc));
                    }
                }
            }
        }

        // 如果上述循环完毕之后，对话还是为空，则加载默认对话
        if(dialogues_list.isEmpty()){
            // 拿到npc的默认对话
            Iterable<NpcDialogue> defaultDialogues = DbMapper.npcDialogueRepository.findNpcDialoguesByNpcAndDefaultDialogue(npc.getDataKey(), true);
            // 如果默认对话不为空
            if(defaultDialogues.iterator().hasNext()){
                //遍历默认对话
                for(NpcDialogue npcDialogue : defaultDialogues){
                    // 获得对话的第一个句子
                    Iterable<DialogueSentence> dialogueSentences = DbMapper.dialogueSentenceRepository.findDialogueSentencesByDialogueOrderByOrdinalAsc(npcDialogue.getDialogue());
                    dialogues_list.add(new Sentence(dialogueSentences.iterator().next(), playerCharacter, npc));
                }
            }else{
                for(String dialogueKey : npc.getDialogues()){
                    Iterable<DialogueSentence> dialogueSentences = DbMapper.dialogueSentenceRepository.findDialogueSentencesByDialogueOrderByOrdinalAsc(dialogueKey);
                    for(DialogueSentence dialogueSentence : dialogueSentences){
                        // 获得对话的第一个句子
                        dialogues_list.add(new Sentence(dialogueSentences.iterator().next(), playerCharacter, npc));
                    }
                }
            }
        }
    }

    public DialogueListMessage(PlayerCharacter playerCharacter, WorldNpcObject npc, String dialogueKey, int sentenceOrder){


        dialogues_list = new ArrayList<>();
        Iterable<DialogueSentence> dialogueSentences = DbMapper.dialogueSentenceRepository.findDialogueSentencesByDialogueOrderByOrdinalAsc(dialogueKey);
        for(DialogueSentence dialogueSentence : dialogueSentences){
            // 先检查前一句是否有事件
            if(dialogueSentence.getOrdinal() == sentenceOrder){
                Iterable<EventData> eventDatas = DbMapper.eventDataRepository.findEventDataByTriggerObject(dialogueSentence.getDataKey());
                for(EventData eventData : eventDatas){
                    GameEventManager.trigger(playerCharacter, eventData);
                }
            }
            // 在查找后一句并添加
            if(dialogueSentence.getOrdinal() == sentenceOrder+1){
                dialogues_list.add(new Sentence(dialogueSentence, playerCharacter, npc));
                break;
            }
        }
    }


    public List<Sentence> getDialogues_list() {
        return dialogues_list;
    }

    public void setDialogues_list(List<Sentence> dialogues_list) {
        this.dialogues_list = dialogues_list;
    }
}
