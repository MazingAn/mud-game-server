package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.DefaultSkills;
import org.springframework.data.repository.CrudRepository;

public interface DefaultSkillsRepository extends CrudRepository<DefaultSkills, Long> {
    Iterable<DefaultSkills> findDefaultSkillsByTarget(String targetKey);
}
