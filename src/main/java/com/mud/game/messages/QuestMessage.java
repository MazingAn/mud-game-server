package com.mud.game.messages;

import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.QuestObject;
import com.mud.game.structs.QuestStatus;
import com.mud.game.worldrun.db.mappings.MongoMapper;

import java.util.ArrayList;
import java.util.List;

public class QuestMessage {

    private List<QuestStatus> quests;


    public QuestMessage(PlayerCharacter playerCharacter) {
        this.quests = new ArrayList<>();
        for(String questId : playerCharacter.getCurrentQuests()){
            QuestObject questObject = MongoMapper.questObjectRepository.findQuestObjectById(questId);
            quests.add(new QuestStatus(playerCharacter, questObject));
        }
    }

    public List<QuestStatus> getQuests() {
        return quests;
    }

    public void setQuests(List<QuestStatus> quests) {
        this.quests = quests;
    }
}
