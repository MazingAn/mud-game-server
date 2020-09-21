package com.mud.game.worlddata.db.models;

import com.mud.game.utils.modelsutils.Mark;

import javax.persistence.*;
import java.util.List;

@Entity
@Mark(name = "合成材料")
public class CompositeMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Mark(name = "编号")
    private Long id;

    @Mark(name = "合成物品标识", link = "allObjects")
    private String dataKey;

    @Mark(name = "数量")
    private int number;

    @Mark(name = "配方标识", link = "allObjects")
    private String dependency;
    //配方名称
    @Transient
    @Mark(name = "数量")
    private String name;
    //配方描述
    @Transient
    @Mark(name = "描述")
    private String description;
    //配方图片
    @Transient
    @Mark(name = "图片")
    private String icon;
    //配方类型
    @Transient
    @Mark(name = "类型")
    private String category;

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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDependency() {
        return dependency;
    }

    public void setDependency(String dependency) {
        this.dependency = dependency;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
