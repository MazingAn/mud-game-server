package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.SkillPosition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SkillPositionRepository extends PagingAndSortingRepository<SkillPosition, Long> {
    SkillPosition findSkillPositionByDataKey(String dataKey);
    SkillPosition deleteSkillPositionById(Long id);
}
