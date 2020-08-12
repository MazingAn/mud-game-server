package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.StrengthenMaterial;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

import java.util.List;

public interface StrengthenMaterialRepository extends SpecificationRepository<StrengthenMaterial, Long> {
    List<StrengthenMaterial> findStrengthenMaterialByDataKey(String datakey);

}
