package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.ActionLearnSkill;
import com.mud.game.worlddata.db.models.ActionMove;
import org.springframework.data.repository.CrudRepository;

public interface ActionMoveRepository extends CrudRepository<ActionMove, Long> {
    ActionMove findActionMoveById(Long id);
    ActionMove findActionMoveByEventKey(String eventKey);
}
