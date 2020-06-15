package com.mud.game.structs;

import com.mud.game.object.manager.GameQuestManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.QuestObject;

import java.util.List;

public class QuestStatus {

    private boolean accomplished;

    private String dbref;

    private String desc;

    private String name;

    private List<ObjectiveShowStatus> objectives;

    private String quest_type;

    public QuestStatus(PlayerCharacter playerCharacter, QuestObject questObject) {
        this.accomplished = questObject.isAccomplished();
        this.dbref = questObject.getId();
        this.desc = questObject.getDescription();
        this.name = questObject.getName();
        this.objectives = GameQuestManager.returnObjectives(playerCharacter, questObject);
        this.quest_type = questObject.getQuestType();
    }

    public boolean isAccomplished() {
        return accomplished;
    }

    public void setAccomplished(boolean accomplished) {
        this.accomplished = accomplished;
    }

    public String getDbref() {
        return dbref;
    }

    public void setDbref(String dbref) {
        this.dbref = dbref;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ObjectiveShowStatus> getObjectives() {
        return objectives;
    }

    public void setObjectives(List<ObjectiveShowStatus> objectives) {
        this.objectives = objectives;
    }

    public String getQuest_type() {
        return quest_type;
    }

    public void setQuest_type(String quest_type) {
        this.quest_type = quest_type;
    }
}
