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
@Mark(name = "挂机类型")
public class HangupType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Mark(name="编号")
    private Long id;

    @Mark(name="标识", link="event")
    private String dataKey;

    @Mark(name="名称", link="quest")
    private String name;

    public HangupType() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
