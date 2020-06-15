package com.mud.game.worlddata.db.models;

import com.mud.game.utils.modelsutils.Mark;

import javax.persistence.*;

/*
* @ 角色模板
* */
@Entity
@Mark(name = "角色模板")
public class CharacterModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Mark(name="编号")
    private Long id;

    @Mark(name="标识")
    private String  dataKey;

    @Mark(name="名称")
    private String name;

    @Mark(name="等级")
    private int level;

    @Mark(name="气血上线")
    // 进入游戏之后的最气血和内力
    private int limit_hp;

    @Mark(name="最大气血")
    private int max_hp;

    @Mark(name="当前血量")
    private int hp;

    @Mark(name="内力上限")
    private int limit_mp;

    @Mark(name="最大内力")
    private int max_mp;

    @Mark(name="内力值")
    private int mp;

    @Mark(name="技能等级")
    // 默认技能等级
    private int skillLevel;

    @Mark(name="最大经验")
    private int max_exp;

    //角色自定义属性
    @Column(length = 4096)
    @Mark(name = "属性")
    private String attrs;

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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLimit_hp() {
        return limit_hp;
    }

    public void setLimit_hp(int limit_hp) {
        this.limit_hp = limit_hp;
    }

    public int getMax_hp() {
        return max_hp;
    }

    public void setMax_hp(int max_hp) {
        this.max_hp = max_hp;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getLimit_mp() {
        return limit_mp;
    }

    public void setLimit_mp(int limit_mp) {
        this.limit_mp = limit_mp;
    }

    public int getMax_mp() {
        return max_mp;
    }

    public void setMax_mp(int max_mp) {
        this.max_mp = max_mp;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }

    public int getMax_exp() {
        return max_exp;
    }

    public void setMax_exp(int max_exp) {
        this.max_exp = max_exp;
    }

    public String getAttrs() {
        return attrs;
    }

    public void setAttrs(String attrs) {
        this.attrs = attrs;
    }
}
