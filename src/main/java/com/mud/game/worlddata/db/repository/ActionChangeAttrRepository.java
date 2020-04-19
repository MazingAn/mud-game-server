package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.ActionAcceptQuest;
import com.mud.game.worlddata.db.models.ActionChangeAttr;
import org.springframework.data.repository.CrudRepository;

public interface ActionChangeAttrRepository extends CrudRepository<ActionChangeAttr, Long> {
    ActionChangeAttr findActionChangeAttrById(Long id);
    ActionChangeAttr findActionChangeAttrByEventKey(String dataKey);
}
