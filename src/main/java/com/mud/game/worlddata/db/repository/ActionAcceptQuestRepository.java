package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.ActionAcceptQuest;
import com.mud.game.worlddata.db.models.ActionAttack;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ActionAcceptQuestRepository extends PagingAndSortingRepository<ActionAcceptQuest, Long> {
    ActionAcceptQuest findActionAcceptQuestById(Long id);
    ActionAcceptQuest findActionAcceptQuestByEventKey(String dataKey);
}
