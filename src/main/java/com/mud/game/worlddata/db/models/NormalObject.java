package com.mud.game.worlddata.db.models;

import com.mud.game.utils.modelsutils.Mark;
import com.mud.game.worlddata.db.models.supermodel.BaseCommonObject;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Mark(name = "普通物品")
public class NormalObject extends BaseCommonObject {
    // 普通物品使用函数
    @Column(length = 1024, nullable = true)
    @Mark(name="使用函数")
    private String actionFunction;

    public String getActionFunction() {
        return actionFunction;
    }

    public void setActionFunction(String actionFunction) {
        this.actionFunction = actionFunction;
    }
}
