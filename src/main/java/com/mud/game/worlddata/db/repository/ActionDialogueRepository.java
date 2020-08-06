package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.ActionAcceptQuest;
import com.mud.game.worlddata.db.models.ActionDialogue;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ActionDialogueRepository extends SpecificationRepository<ActionDialogue, Long> {
    ActionDialogue findActionDialogueById(Long id);
    ActionDialogue findActionDialogueByEventKey(String dataKey);
}
