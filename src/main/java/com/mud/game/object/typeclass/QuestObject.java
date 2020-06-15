package com.mud.game.object.typeclass;

import com.mud.game.structs.ObjectiveStatus;
import com.mud.game.worlddata.db.models.QuestRewardList;

import javax.persistence.Id;
import java.util.Set;

public class QuestObject {
    @Id
    private String id;

    private String dataKey;

    private String owner;

    private String status;

    private String questType;

    private String name;

    private String description;

    private boolean accomplished;

    private Set<ObjectiveStatus> objectives;

    private Set<QuestRewardList> rewards;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQuestType() {
        return questType;
    }

    public void setQuestType(String questType) {
        this.questType = questType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ObjectiveStatus> getObjectives() {
        return objectives;
    }

    public void setObjectives(Set<ObjectiveStatus> objectives) {
        this.objectives = objectives;
    }

    public Set<QuestRewardList> getRewards() {
        return rewards;
    }

    public void setRewards(Set<QuestRewardList> rewards) {
        this.rewards = rewards;
    }

    public boolean isAccomplished() {
        return accomplished;
    }

    public void setAccomplished(boolean accomplished) {
        this.accomplished = accomplished;
    }
}
