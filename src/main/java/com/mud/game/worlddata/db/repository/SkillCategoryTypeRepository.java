package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.SkillCategoryType;
import org.springframework.data.repository.CrudRepository;

public interface SkillCategoryTypeRepository extends CrudRepository<SkillCategoryType, Long> {
    SkillCategoryType findSkillCategoryTypeByDataKey(String dataKey);
}
