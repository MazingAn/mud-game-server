package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.ActionAttack;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

public interface ActionAttackRepository extends SpecificationRepository<ActionAttack, Long> {
    ActionAttack findActionAttackById(Long id);
    ActionAttack findActionAttackByEventKey(String eventKey);
}
