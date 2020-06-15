package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.QuestDependency;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface QuestDependencyRepository extends PagingAndSortingRepository<QuestDependency, Long> {
}
