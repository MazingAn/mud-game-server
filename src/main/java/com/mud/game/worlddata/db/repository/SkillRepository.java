package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.Skill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SkillRepository extends PagingAndSortingRepository<Skill, Long> {
    Skill findSkillByDataKey(String dataKey);
    Iterable<Skill>  findSkillsByPassive(boolean isPassive);
    Iterable<Skill> findSkillsByPassiveAndSchool(boolean isPassive, String schoolKey);
    Iterable<Skill> findSkillsByPassiveAndCategoryType(boolean isPassive, String categoryType);
    boolean existsByDataKey(String dataKey);
}
