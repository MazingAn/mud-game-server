package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.ActionAcceptQuest;
import com.mud.game.worlddata.db.models.ActionDialogue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ActionDialogueRepository extends PagingAndSortingRepository<ActionDialogue, Long> {
    ActionDialogue findActionDialogueById(Long id);
    ActionDialogue findActionDialogueByEventKey(String dataKey);
}
