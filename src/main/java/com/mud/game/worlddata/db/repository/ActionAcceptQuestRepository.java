package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.ActionAcceptQuest;
import com.mud.game.worlddata.db.models.ActionAttack;
import org.springframework.data.repository.CrudRepository;

public interface ActionAcceptQuestRepository extends CrudRepository<ActionAcceptQuest, Long> {
    ActionAcceptQuest findActionAcceptQuestById(Long id);
    ActionAcceptQuest findActionAcceptQuestByEventKey(String dataKey);
}
