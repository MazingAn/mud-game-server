package com.mud.game.worlddata.db.models;

import com.mud.game.utils.modelsutils.Mark;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"target", "common_object"}))
@Mark(name = "默认物品")
public class DefaultObjects {
    /*
    * @ 玩家所拥有的默认物品
    * */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Mark(name="编号")
    private Long id;

    @Column(name="target")
    @Mark(name="目标", link = "characterModels")
    private String target;

    @Column(name = "common_object")
    @Mark(name="物品", link = "allObjects")
    private String commonObject;

    @Mark(name="数量")
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
