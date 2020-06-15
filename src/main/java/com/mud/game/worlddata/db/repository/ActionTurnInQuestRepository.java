package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.ActionTurnInQuest;
import com.mud.game.worlddata.db.models.EventData;
import org.springframework.data.repository.CrudRepository;

public interface ActionTurnInQuestRepository extends CrudRepository<ActionTurnInQuest, Long> {
    ActionTurnInQuest findActionTurnInQuestById(Long id);
    ActionTurnInQuest findActionTurnInQuestByEventKey(String dataKey);
}
