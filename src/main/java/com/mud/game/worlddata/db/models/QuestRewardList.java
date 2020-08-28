package com.mud.game.worlddata.db.models;

import com.mud.game.utils.modelsutils.Mark;

import javax.persistence.*;

/**
 * 任务奖励表
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"provider", "object"}))
@Mark(name = "任务奖励表")
public class QuestRewardList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Mark(name = "编号")
    private Long id;

    @Mark(name = "任务", link = "quest")
    private String provider;

    @Mark(name = "任务奖励", link = "allObjects")
    private String object;

    @Mark(name = "数量")
    private int number;


    @Mark(name = "概率")
    private float odds;

    @Mark(name = "触发条件")
    private String actionCondition;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
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

    public float getOdds() {
        return odds;
    }

    public void setOdds(float odds) {
        this.odds = odds;
    }

    public String getActionCondition() {
        return actionCondition;
    }

    public void setActionCondition(String actionCondition) {
        this.actionCondition = actionCondition;
    }
}
