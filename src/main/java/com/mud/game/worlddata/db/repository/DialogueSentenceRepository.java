package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.DialogueSentence;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DialogueSentenceRepository extends PagingAndSortingRepository<DialogueSentence, Long> {
    Iterable<DialogueSentence> findDialogueSentencesByDialogueOrderByOrdinalAsc(String dialogueKey);
}
