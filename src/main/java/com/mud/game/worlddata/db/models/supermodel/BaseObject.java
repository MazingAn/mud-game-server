package com.mud.game.worlddata.db.models.supermodel;

import com.mud.game.utils.modelsutils.Mark;

import javax.persistence.*;

/*
* 所有游戏中存在的物体的Entity的超类
* */
@MappedSuperclass
public class BaseObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Mark(name="编号")
    private Long id;

    @Column(unique = true)
    @Mark(name="标识")
    private String dataKey;

    @Mark(name="类型类")
    private String typeClass;

    @Column(length = 64)
    @Mark(name="名称")
    private String name;

    @Column(length = 2048)
    @Mark(name="描述")
    private String description;

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

    public String getTypeClass() {
        return typeClass;
    }

    public void setTypeClass(String typeClass) {
        this.typeClass = typeClass;
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
}
