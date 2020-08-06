package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.ActionAcceptQuest;
import com.mud.game.worlddata.db.models.Skill;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SkillRepository extends SpecificationRepository<Skill, Long> {
    Skill findSkillByDataKey(String dataKey);
    Iterable<Skill>  findSkillsByPassive(boolean isPassive);
    Iterable<Skill> findSkillsByPassiveAndSchool(boolean isPassive, String schoolKey);
    Iterable<Skill> findSkillsByPassiveAndCategoryType(boolean isPassive, String categoryType);
    boolean existsByDataKey(String dataKey);
}
