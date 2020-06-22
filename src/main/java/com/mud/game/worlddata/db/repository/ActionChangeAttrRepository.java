package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.ActionAcceptQuest;
import com.mud.game.worlddata.db.models.ActionChangeAttr;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ActionChangeAttrRepository extends PagingAndSortingRepository  <ActionChangeAttr, Long> {
    ActionChangeAttr findActionChangeAttrById(Long id);
    ActionChangeAttr findActionChangeAttrByEventKey(String dataKey);
}
