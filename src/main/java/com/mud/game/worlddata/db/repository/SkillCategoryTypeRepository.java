package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.SkillCategoryType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SkillCategoryTypeRepository extends PagingAndSortingRepository<SkillCategoryType, Long> {
    SkillCategoryType findSkillCategoryTypeByDataKey(String dataKey);
}
