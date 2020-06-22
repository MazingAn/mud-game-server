package com.mud.game.worlddata.db.models;

import com.mud.game.utils.modelsutils.Mark;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Mark(name = "物品类型")
public class ObjectType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Mark(name = "id")
    private long id;

    @Mark(name = "标识")
    @NotNull
    private String dataKey;

    @Mark(name = "描述")
    @NotNull
    private String name;

    public ObjectType(){}

    public ObjectType(String key, String name) {
        this.dataKey = key;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
