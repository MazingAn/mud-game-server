package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.ActionAttack;
import com.mud.game.worlddata.db.models.ActionTurnInQuest;
import org.springframework.data.repository.CrudRepository;

public interface ActionAttackRepository extends CrudRepository<ActionAttack, Long> {
    ActionAttack findActionAttackById(Long id);
    ActionAttack findActionAttackByEventKey(String eventKey);
}
