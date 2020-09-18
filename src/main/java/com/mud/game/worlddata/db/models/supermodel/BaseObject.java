package com.mud.game.worlddata.db.models.supermodel;

import com.mud.game.utils.modelsutils.Mark;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    @NotBlank(message = "数据标识不能为空")
    @Size(min=2, max=64, message = "标识长度必须在2到64之间")
    private String dataKey;


    @Mark(name="名称")
    @Column(length = 64)
    private String name;

    @Column(length = 2048)
    @Mark(name="描述")
    @Size(max = 2048, message = "最大长度不得超过2048")
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
