package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.NpcDialogue;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

public interface NpcDialogueRepository extends SpecificationRepository<NpcDialogue, Long> {
    Iterable<NpcDialogue> findNpcDialoguesByNpc(String npc);
    Iterable<NpcDialogue> findNpcDialoguesByNpcAndDefaultDialogue(String npc, Boolean isDefault);
}
