package com.mud.game.worlddata.db.models;

import com.mud.game.worlddata.db.models.supermodel.BaseObject;
import com.mud.game.worlddata.db.models.supermodel.CommonObject;

import javax.persistence.*;

@Entity
public class SkillBook extends CommonObject {
    /* 技能书（秘籍）
     * 可以从技能书上学习对应的技能
     */

    //技能书最大支持等级
    private int max_level;

    //使用秘籍的时候是否消耗潜能
    @Column(columnDefinition = "bool default true")
    private boolean use_potential;

    //技能书品级
    private int  quality;

    //使用条件
    @Column(length = 1024)
    private String useCondition;


    public int getMax_level() {
        return max_level;
    }

    public void setMax_level(int max_level) {
        this.max_level = max_level;
    }

    public boolean isUse_potential() {
        return use_potential;
    }

    public void setUse_potential(boolean use_potential) {
        this.use_potential = use_potential;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public String getUseCondition() {
        return useCondition;
    }

    public void setUseCondition(String useCondition) {
        this.useCondition = useCondition;
    }
}
