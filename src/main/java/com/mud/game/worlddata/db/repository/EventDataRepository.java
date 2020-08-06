package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.EventData;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

public interface EventDataRepository extends SpecificationRepository<EventData, Long> {
    EventData findEventDataById(Long id);
    EventData findEventDataByDataKey(String dataKey);
    Iterable<EventData> findEventDataByTriggerObject(String objKey);
    Iterable<EventData> findEventDataByTriggerType(String triggerType);
}
