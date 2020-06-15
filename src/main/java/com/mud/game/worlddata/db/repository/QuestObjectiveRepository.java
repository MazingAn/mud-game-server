package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.QuestObjective;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface QuestObjectiveRepository extends PagingAndSortingRepository<QuestObjective, Long> {
    Iterable<QuestObjective> findQuestObjectivesByQuest(String questKey);
}
