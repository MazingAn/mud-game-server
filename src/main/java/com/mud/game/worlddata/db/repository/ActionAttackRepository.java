package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.ActionAttack;
import com.mud.game.worlddata.db.models.ActionTurnInQuest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ActionAttackRepository extends PagingAndSortingRepository<ActionAttack, Long> {
    ActionAttack findActionAttackById(Long id);
    ActionAttack findActionAttackByEventKey(String eventKey);
}
