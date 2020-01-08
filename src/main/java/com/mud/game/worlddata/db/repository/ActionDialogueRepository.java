package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.ActionAcceptQuest;
import com.mud.game.worlddata.db.models.ActionDialogue;
import org.springframework.data.repository.CrudRepository;

public interface ActionDialogueRepository extends CrudRepository<ActionDialogue, Long> {
    public ActionDialogue findActionDialogueById(Long id);
    public ActionDialogue findActionDialogueByEventKey(String dataKey);
}
