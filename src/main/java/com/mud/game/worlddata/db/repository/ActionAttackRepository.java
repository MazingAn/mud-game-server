package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.ActionAttack;
import com.mud.game.worlddata.db.models.ActionTurnInQuest;
import org.springframework.data.repository.CrudRepository;

public interface ActionAttackRepository extends CrudRepository<ActionAttack, Long> {
    public ActionAttack findActionAttackById(Long id);
    public ActionAttack findActionAttackByEventKey(String eventKey);
}
