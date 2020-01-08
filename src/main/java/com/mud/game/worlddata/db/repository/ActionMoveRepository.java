package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.ActionLearnSkill;
import com.mud.game.worlddata.db.models.ActionMove;
import org.springframework.data.repository.CrudRepository;

public interface ActionMoveRepository extends CrudRepository<ActionMove, Long> {
    public ActionMove findActionMoveById(Long id);
    public ActionMove findActionMoveByEventKey(String eventKey);
}
