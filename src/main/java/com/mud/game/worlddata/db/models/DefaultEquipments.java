package com.mud.game.worlddata.db.models;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"target", "equipment_key"}))
public class DefaultEquipments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="target")
    private String target;
    @Column(name="equipment_key")
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
