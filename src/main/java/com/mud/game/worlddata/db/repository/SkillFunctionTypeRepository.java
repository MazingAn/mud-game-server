package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.SkillFunctionType;
import org.springframework.data.repository.CrudRepository;

public interface SkillFunctionTypeRepository extends CrudRepository<SkillFunctionType, Long> {
    SkillFunctionType findSkillFunctionTypeByDataKey(String dataKey);
}
