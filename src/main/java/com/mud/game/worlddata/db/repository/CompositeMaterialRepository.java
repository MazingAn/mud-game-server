package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.CompositeMaterial;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

import java.util.List;

public interface CompositeMaterialRepository extends SpecificationRepository<CompositeMaterial, Long> {
    List<CompositeMaterial> findCompositeMaterialByDataKey(String datakey);
}
