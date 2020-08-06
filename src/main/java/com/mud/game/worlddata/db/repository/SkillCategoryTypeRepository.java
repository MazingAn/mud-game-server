package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.SkillCategoryType;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

public interface SkillCategoryTypeRepository extends SpecificationRepository<SkillCategoryType, Long> {
    SkillCategoryType findSkillCategoryTypeByDataKey(String dataKey);
}
