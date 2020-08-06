package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.ActionAcceptQuest;
import com.mud.game.worlddata.db.models.ActionAttack;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ActionAttackRepository extends SpecificationRepository<ActionAttack, Long> {
    ActionAttack findActionAttackById(Long id);
    ActionAttack findActionAttackByEventKey(String eventKey);
}
