package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.Dialogue;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

public interface DialogueRepository extends SpecificationRepository<Dialogue, Long> {
    Dialogue findDialogueByDataKey(String dataKey);
}
