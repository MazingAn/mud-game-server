package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.EquipmentPosition;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

public interface EquipmentPositionRepository extends SpecificationRepository<EquipmentPosition, Long> {
    EquipmentPosition findEquipmentPositionByDataKey(String dataKey);
}
