package com.mud.game.worlddata.db.models;

import com.mud.game.utils.modelsutils.Mark;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 游戏对话
 * */
@Entity
@Mark(name = "对话")
public class Dialogue {
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
    private String name;

    @Mark(name="触发条件")
    private String actionCondition;

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

    public String getActionCondition() {
        return actionCondition;
    }

    public void setActionCondition(String actionCondition) {
        this.actionCondition = actionCondition;
    }
}
