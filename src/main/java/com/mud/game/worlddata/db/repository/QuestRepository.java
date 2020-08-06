package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.Quest;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

public interface QuestRepository extends SpecificationRepository<Quest, Long> {
    Quest findQuestByDataKey(String dataKey);
}
