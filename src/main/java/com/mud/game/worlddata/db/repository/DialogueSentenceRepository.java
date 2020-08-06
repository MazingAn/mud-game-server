package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.DialogueSentence;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

public interface DialogueSentenceRepository extends SpecificationRepository<DialogueSentence, Long> {
    Iterable<DialogueSentence> findDialogueSentencesByDialogueOrderByOrdinalAsc(String dialogueKey);
}
