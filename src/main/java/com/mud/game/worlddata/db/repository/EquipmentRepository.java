package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.Equipment;
import org.springframework.data.repository.CrudRepository;

public interface EquipmentRepository extends CrudRepository<Equipment, Long> {
    Equipment findEquipmentByDataKey(String dataKey);
}
