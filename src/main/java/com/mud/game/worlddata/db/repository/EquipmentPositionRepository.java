package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.EquipmentPosition;
import com.mud.game.worlddata.db.models.SkillPosition;
import org.springframework.data.repository.CrudRepository;

public interface EquipmentPositionRepository extends CrudRepository<EquipmentPosition, Long> {
    EquipmentPosition findEquipmentPositionByDataKey(String dataKey);
}
