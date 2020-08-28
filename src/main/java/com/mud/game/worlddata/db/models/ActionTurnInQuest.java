package com.mud.game.worlddata.db.models;

import com.mud.game.utils.modelsutils.Mark;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Mark(name = "提交任务事件")
public class ActionTurnInQuest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Mark(name="编号")
    private Long id;

    @Mark(name="事件", link = "event")
    private String eventKey;
    @Mark(name="任务", link="quest")
    private String questKey;



    public ActionTurnInQuest() {
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
}
