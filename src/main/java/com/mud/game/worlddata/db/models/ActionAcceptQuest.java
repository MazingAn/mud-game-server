package com.mud.game.worlddata.db.models;

import com.mud.game.utils.modelsutils.Mark;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
/**
 * 接受任务事件
 * */
@Mark(name = "接受任务事件")
public class ActionAcceptQuest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Mark(name = "编号")
    private Long id;

    @Mark(name = "描述名称")
    private String name;

    @Mark(name = "事件", link = "event")
    private String eventKey;

    @Mark(name = "任务", link = "quest")
    private String questKey;

    public ActionAcceptQuest() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getQuestKey() {
        return questKey;
    }

    public void setQuestKey(String questKey) {
        this.questKey = questKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
