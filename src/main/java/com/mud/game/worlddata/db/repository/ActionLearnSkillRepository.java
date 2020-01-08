package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.ActionAcceptQuest;
import com.mud.game.worlddata.db.models.ActionLearnSkill;
import org.springframework.data.repository.CrudRepository;

public interface ActionLearnSkillRepository extends CrudRepository<ActionLearnSkill, Long> {
    public ActionLearnSkill findActionLearnSkillById(Long id);
    public ActionLearnSkill findActionLearnSkillByEventKey(String dataKey);
}
