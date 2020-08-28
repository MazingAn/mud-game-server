package com.mud.game.worlddata.db.models;


import com.mud.game.utils.modelsutils.Mark;

import javax.persistence.*;

/**
 * 任务需求表
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"quest", "object"}))
@Mark(name = "任务目标")
public class QuestObjective {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Mark(name = "编号")
    private Long id;

    @Mark(name = "任务", link = "quest")
    private String quest;

    @Mark(name = "目标类型", link = "objectiveType")
    private String objectiveType;

    @Mark(name = "目标", link = "objectiveTypeObject")
    private String object;


    @Mark(name = "数量")
    private int number;

    @Mark(name = "描述")
    private String description;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuest() {
        return quest;
    }

    public void setQuest(String quest) {
        this.quest = quest;
    }

    public String getObjectiveType() {
        return objectiveType;
    }

    public void setObjectiveType(String objectiveType) {
        this.objectiveType = objectiveType;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
