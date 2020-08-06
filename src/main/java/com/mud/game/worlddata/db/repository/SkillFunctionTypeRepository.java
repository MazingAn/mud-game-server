package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.SkillFunctionType;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

public interface SkillFunctionTypeRepository extends SpecificationRepository<SkillFunctionType, Long> {
    SkillFunctionType findSkillFunctionTypeByDataKey(String dataKey);
    SkillFunctionType deleteSkillFunctionTypeById(Long id);
}
