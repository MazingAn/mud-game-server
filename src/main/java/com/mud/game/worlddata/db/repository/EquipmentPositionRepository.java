package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.EquipmentPosition;
import com.mud.game.worlddata.db.models.SkillPosition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EquipmentPositionRepository extends PagingAndSortingRepository<EquipmentPosition, Long> {
    EquipmentPosition findEquipmentPositionByDataKey(String dataKey);
}
