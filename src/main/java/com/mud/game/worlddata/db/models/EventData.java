package com.mud.game.worlddata.db.models;


import com.mud.game.utils.modelsutils.Mark;

import javax.persistence.*;

@Entity
public class EventData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Mark(name="编号")
    private Long id;
    @Column(unique = true)
    @Mark(name="标识")
    private String dataKey;
    @Mark(name="触发对象")
    private String triggerObject;
    @Mark(name="触发类型")
    private String triggerType;
    @Mark(name="触发动作")
    private String action;
    @Mark(name="动作名称")
    private String actionName;
    @Mark(name="概率")
    private String odds;
    @Mark(name="描述")
    @Column(length = 4096)
    private String triggerCondition;

    public EventData() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTriggerObject() {
        return triggerObject;
    }

    public void setTriggerObject(String triggerObject) {
        this.triggerObject = triggerObject;
    }

    public String getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getOdds() {
        return odds;
    }

    public void setOdds(String odds) {
        this.odds = odds;
    }

    public String getTriggerCondition() {
        return triggerCondition;
    }

    public void setTriggerCondition(String triggerCondition) {
        this.triggerCondition = triggerCondition;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }
}
