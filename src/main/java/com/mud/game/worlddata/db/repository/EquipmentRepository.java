package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.Equipment;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

public interface EquipmentRepository extends SpecificationRepository<Equipment, Long> {
    Equipment findEquipmentByDataKey(String dataKey);
    boolean existsByDataKey(String dataKey);
}
