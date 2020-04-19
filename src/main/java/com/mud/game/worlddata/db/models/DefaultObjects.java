package com.mud.game.worlddata.db.models;

import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"target", "common_object"}))
public class DefaultObjects {
    /*
    * @ 玩家所拥有的默认物品
    * */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="target")
    private String target;
    @Column(name = "common_object")
    private String commonObject;
    private int number;

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

    public String getCommonObject() {
        return commonObject;
    }

    public void setCommonObject(String commonObject) {
        this.commonObject = commonObject;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
