package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.ActionAcceptQuest;
import com.mud.game.worlddata.db.models.ActionChangeAttr;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ActionChangeAttrRepository extends SpecificationRepository<ActionChangeAttr, Long> {
    ActionChangeAttr findActionChangeAttrById(Long id);
    ActionChangeAttr findActionChangeAttrByEventKey(String dataKey);
}
