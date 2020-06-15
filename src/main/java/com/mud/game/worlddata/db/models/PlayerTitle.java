package com.mud.game.worlddata.db.models;

import com.mud.game.utils.modelsutils.Mark;

import javax.persistence.*;


@Entity
public class PlayerTitle {
    /*
    游戏中的称号，以及称号的作用
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Mark(name="编号")
    private int id;
    @Mark(name="标识")
    @Column(unique = true)
    private String dataKey;
    @Mark(name="名称")
    private String name;
    @Mark(name="描述")
    private String description;
    @Mark(name="效果")
    @Column(length = 512)
    private String effect;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }
}
