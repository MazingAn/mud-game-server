package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.ActionAcceptQuest;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ActionAcceptQuestRepository extends SpecificationRepository<ActionAcceptQuest, Long> {
    ActionAcceptQuest findActionAcceptQuestById(Long id);
    ActionAcceptQuest findActionAcceptQuestByEventKey(String dataKey);
}
