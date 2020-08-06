package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.ActionTurnInQuest;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

public interface ActionTurnInQuestRepository extends SpecificationRepository<ActionTurnInQuest, Long> {
    ActionTurnInQuest findActionTurnInQuestById(Long id);
    ActionTurnInQuest findActionTurnInQuestByEventKey(String dataKey);
}
