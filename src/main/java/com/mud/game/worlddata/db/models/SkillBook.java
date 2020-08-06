package com.mud.game.worlddata.db.models;

import com.mud.game.utils.modelsutils.Mark;
import com.mud.game.worlddata.db.models.supermodel.BaseCommonObject;

import javax.persistence.*;

@Entity
@Mark(name="技能书")
public class SkillBook extends BaseCommonObject {
    /* 技能书（秘籍）
     * 可以从技能书上学习对应的技能
     */
    @Mark(name="最大等级")
    //技能书最大支持等级
    private int max_level;


    //使用秘籍的时候是否消耗潜能
    @Mark(name="是否消耗潜能")
    @Column(columnDefinition = "bool default true")
    private boolean use_potential;

    //技能书品级
    @Mark(name="品级")
    private int  quality;
    @Mark(name = "描述名称")
    private String name;
    //使用条件
    @Mark(name="使用条件")
    @Column(length = 1024)
    private String useCondition;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

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
