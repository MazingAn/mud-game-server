package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.ActionAcceptQuest;
import com.mud.game.worlddata.db.models.ActionLearnSkill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ActionLearnSkillRepository extends PagingAndSortingRepository<ActionLearnSkill, Long> {
    ActionLearnSkill findActionLearnSkillById(Long id);
    ActionLearnSkill findActionLearnSkillByEventKey(String dataKey);
}
