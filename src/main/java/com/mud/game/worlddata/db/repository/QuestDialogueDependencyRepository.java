package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.QuestDialogueDependency;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

public interface QuestDialogueDependencyRepository extends SpecificationRepository<QuestDialogueDependency, Long> {
    Iterable<QuestDialogueDependency> findQuestDialogueDependenciesByDialogue(String dialogueKey);
}
