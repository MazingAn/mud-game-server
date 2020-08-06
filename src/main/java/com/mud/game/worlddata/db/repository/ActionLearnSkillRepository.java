package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.ActionAcceptQuest;
import com.mud.game.worlddata.db.models.ActionLearnSkill;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ActionLearnSkillRepository extends SpecificationRepository<ActionLearnSkill, Long> {
    ActionLearnSkill findActionLearnSkillById(Long id);
    ActionLearnSkill findActionLearnSkillByEventKey(String dataKey);
}
