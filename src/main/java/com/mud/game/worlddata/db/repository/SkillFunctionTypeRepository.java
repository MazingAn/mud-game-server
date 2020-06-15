package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.SkillFunctionType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SkillFunctionTypeRepository extends PagingAndSortingRepository<SkillFunctionType, Long> {
    SkillFunctionType findSkillFunctionTypeByDataKey(String dataKey);
    SkillFunctionType deleteSkillFunctionTypeById(Long id);
}
