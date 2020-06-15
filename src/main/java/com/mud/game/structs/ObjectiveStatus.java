package com.mud.game.structs;

import com.mud.game.worlddata.db.models.QuestObjective;

public class ObjectiveStatus {
    private String objectiveType;
    private String object;
    private String target;
    private int accomplished;
    private int total;

    public ObjectiveStatus() {
    }

    public ObjectiveStatus(QuestObjective objective) {
        this.accomplished = 0;
        this.object = objective.getObject();
        this.objectiveType = objective.getObjectiveType();
        this.total = objective.getNumber();
    }

    public int getAccomplished() {
        return accomplished;
    }

    public void setAccomplished(int accomplished) {
        this.accomplished = accomplished;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getObjectiveType() {
        return objectiveType;
    }

    public void setObjectiveType(String objectiveType) {
        this.objectiveType = objectiveType;
    }
}
