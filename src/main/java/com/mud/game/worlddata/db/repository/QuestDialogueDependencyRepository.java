package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.QuestDialogueDependency;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface QuestDialogueDependencyRepository extends PagingAndSortingRepository<QuestDialogueDependency, Long> {
    Iterable<QuestDialogueDependency> findQuestDialogueDependenciesByDialogue(String dialogueKey);
}
