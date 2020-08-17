package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.QualityMaterial;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

import java.util.List;

public interface QualityMaterialRepository extends SpecificationRepository<QualityMaterial, Long> {
    List<QualityMaterial> findQualityMaterialByDataKeyAndQuality(String dataKey, int quality);
}
