package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.ActionAcceptQuest;
import com.mud.game.worlddata.db.models.ActionMove;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ActionMoveRepository extends SpecificationRepository<ActionMove, Long> {
    ActionMove findActionMoveById(Long id);
    ActionMove findActionMoveByEventKey(String eventKey);
}
