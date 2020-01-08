package com.mud.game.worlddata.db.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"skillBook", "skill"}))
public class SkillBookBind {

    @Id
    private Long id;
    private String skillBook;
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
}
