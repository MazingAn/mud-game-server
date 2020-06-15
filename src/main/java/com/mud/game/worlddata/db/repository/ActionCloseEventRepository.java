package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.ActionAcceptQuest;
import com.mud.game.worlddata.db.models.ActionCloseEvent;
import org.springframework.data.repository.CrudRepository;

public interface ActionCloseEventRepository extends CrudRepository<ActionCloseEvent, Long> {
    ActionCloseEvent findActionCloseEventById(Long id);
    ActionCloseEvent findActionCloseEventByEventKey(String dataKey);
}
