package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.SkillPosition;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

public interface SkillPositionRepository extends SpecificationRepository<SkillPosition, Long> {
    SkillPosition findSkillPositionByDataKey(String dataKey);
    SkillPosition deleteSkillPositionById(Long id);
}
