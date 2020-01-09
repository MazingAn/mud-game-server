package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.SkillPosition;
import org.springframework.data.repository.CrudRepository;

public interface SkillPositionRepository extends CrudRepository<SkillPosition, Long> {
    SkillPosition findSkillPositionByDataKey(String dataKey);
}
