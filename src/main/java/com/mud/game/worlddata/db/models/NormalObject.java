package com.mud.game.worlddata.db.models;

import com.mud.game.worlddata.db.models.supermodel.BaseCommonObject;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class NormalObject extends BaseCommonObject {
    // 普通物品使用函数
    @Column(length = 1024, nullable = true)
    private String actionFunction;

    public String getActionFunction() {
        return actionFunction;
    }

    public void setActionFunction(String actionFunction) {
        this.actionFunction = actionFunction;
    }
}
