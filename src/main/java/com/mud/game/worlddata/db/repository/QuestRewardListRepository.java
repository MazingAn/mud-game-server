package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.QuestRewardList;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface QuestRewardListRepository extends PagingAndSortingRepository<QuestRewardList, Long> {
    Iterable<QuestRewardList> findQuestRewardListsByProvider(String questKey);
}
