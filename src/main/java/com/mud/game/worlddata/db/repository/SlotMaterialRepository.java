package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.SlotMaterial;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

import java.util.List;

public interface SlotMaterialRepository extends SpecificationRepository<SlotMaterial, Long> {
    List<SlotMaterial> findSlotMaterialByDataKeyAndSlotNumber(String dataKey, int slotNumber);
}
