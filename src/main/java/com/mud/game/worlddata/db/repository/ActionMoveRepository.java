package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.ActionLearnSkill;
import com.mud.game.worlddata.db.models.ActionMove;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ActionMoveRepository extends PagingAndSortingRepository<ActionMove, Long> {
    ActionMove findActionMoveById(Long id);
    ActionMove findActionMoveByEventKey(String eventKey);
}
