package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.ActionTurnInQuest;
import com.mud.game.worlddata.db.models.EventData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ActionTurnInQuestRepository extends PagingAndSortingRepository<ActionTurnInQuest, Long> {
    ActionTurnInQuest findActionTurnInQuestById(Long id);
    ActionTurnInQuest findActionTurnInQuestByEventKey(String dataKey);
}
