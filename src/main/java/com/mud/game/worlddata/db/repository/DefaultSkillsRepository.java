package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.DefaultSkills;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DefaultSkillsRepository extends PagingAndSortingRepository<DefaultSkills, Long> {
    Iterable<DefaultSkills> findDefaultSkillsByTarget(String targetKey);
}
