package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.NpcDialogue;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NpcDialogueRepository extends PagingAndSortingRepository<NpcDialogue, Long> {
    Iterable<NpcDialogue> findNpcDialoguesByNpc(String npc);
    Iterable<NpcDialogue> findNpcDialoguesByNpcAndDefaultDialogue(String npc, Boolean isDefault);
}
