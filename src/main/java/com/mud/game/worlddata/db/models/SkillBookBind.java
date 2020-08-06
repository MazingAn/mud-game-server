package com.mud.game.worlddata.db.models;

import com.mud.game.utils.modelsutils.Mark;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"skill_book", "skill"}))
@Mark(name = "技能书绑定")
public class SkillBookBind {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Mark(name="编号")
    private Long id;
    @Mark(name = "描述名称")
    private String name;
    @Mark(name="技能书", link = "skillBook")
    @Column(name = "skill_book")
    private String skillBook;
    @Mark(name="技能", link="skill")
    @Column(name = "skill")
    private String skill;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkillBook() {
        return skillBook;
    }

    public void setSkillBook(String skillBook) {
        this.skillBook = skillBook;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
