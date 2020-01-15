package com.mud.game.worlddata.db.models;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"target", "skill_key", "level"}))
public class DefaultSkills {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "target")
    private String target;
    @Column(name = "skill_key")
    private String skillKey;
    private boolean equipped; //是否默认装备技能
    private boolean teachIt; //徒弟是否可以学习
    private String position; // 如果技能是可以装备的 那么装备到那个位置
    @Column(name = "level")
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
