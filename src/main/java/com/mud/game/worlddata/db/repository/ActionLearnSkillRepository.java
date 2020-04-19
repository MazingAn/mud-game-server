package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.ActionAcceptQuest;
import com.mud.game.worlddata.db.models.ActionLearnSkill;
import org.springframework.data.repository.CrudRepository;

public interface ActionLearnSkillRepository extends CrudRepository<ActionLearnSkill, Long> {
    ActionLearnSkill findActionLearnSkillById(Long id);
    ActionLearnSkill findActionLearnSkillByEventKey(String dataKey);
}
