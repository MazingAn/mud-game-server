package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.ActionAcceptQuest;
import com.mud.game.worlddata.db.models.ActionCloseEvent;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ActionCloseEventRepository extends SpecificationRepository<ActionCloseEvent, Long> {
    ActionCloseEvent findActionCloseEventById(Long id);
    ActionCloseEvent findActionCloseEventByEventKey(String dataKey);
}
