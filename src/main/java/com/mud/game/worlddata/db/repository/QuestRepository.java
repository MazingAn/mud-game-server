package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.Quest;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface QuestRepository extends PagingAndSortingRepository<Quest, Long> {
    Quest findQuestByDataKey(String dataKey);
}
