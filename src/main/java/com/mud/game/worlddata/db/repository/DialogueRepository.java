package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.Dialogue;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DialogueRepository extends PagingAndSortingRepository<Dialogue, Long> {
    Dialogue findDialogueByDataKey(String dataKey);
}
