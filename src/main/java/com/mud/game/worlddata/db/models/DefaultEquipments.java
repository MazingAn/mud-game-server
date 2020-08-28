package com.mud.game.worlddata.db.models;

import com.mud.game.utils.modelsutils.Mark;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"target", "equipment_key"}))
@Mark(name = "npc默认装备")
public class DefaultEquipments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Mark(name="编号")
    private Long id;

    @Column(name="target")
    @Mark(name="目标", link="npc")
    private String target;
    @Column(name="equipment_key")
    @Mark(name="装备", link="equipment")
    private String equipmentKey;

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

    public String getEquipmentKey() {
        return equipmentKey;
    }

    public void setEquipmentKey(String equipmentKey) {
        this.equipmentKey = equipmentKey;
    }
}
