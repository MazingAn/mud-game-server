package com.mud.game.worlddata.db.models;

import com.mud.game.utils.modelsutils.Mark;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"target", "skill_key", "level"}))
@Mark(name = "默认技能")
public class DefaultSkills {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Mark(name="编号")
    private Long id;

    @Column(name = "target")
    @Mark(name="目标", link="npc")
    private String target;

    @Column(name = "skill_key")
    @Mark(name="技能", link="skill")
    private String skillKey;

    @Mark(name="是否装备")
    private boolean equipped; //是否默认装备技能

    @Mark(name="是否教授")
    private boolean teachIt; //徒弟是否可以学习

    @Mark(name="装备位置", link = "skillPosition")
    private String position; // 如果技能是可以装备的 那么装备到那个位置

    @Column(name = "level")
    @Mark(name="等级")
    private int level; //默认技能的等级

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getSkillKey() {
        return skillKey;
    }

    public void setSkillKey(String skillKey) {
        this.skillKey = skillKey;
    }


    public boolean isEquipped() {
        return equipped;
    }

    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }

    public boolean isTeachIt() {
        return teachIt;
    }

    public void setTeachIt(boolean teachIt) {
        this.teachIt = teachIt;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
