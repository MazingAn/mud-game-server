package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.QuestRewardList;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

public interface QuestRewardListRepository extends SpecificationRepository<QuestRewardList, Long> {
    Iterable<QuestRewardList> findQuestRewardListsByProvider(String questKey);
}
