package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.CharacterModel;
import com.mud.game.worlddata.db.models.EventData;
import org.apache.poi.ss.formula.functions.Even;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EventDataRepository extends PagingAndSortingRepository<EventData, Long> {
    EventData findEventDataById(Long id);
    EventData findEventDataByDataKey(String dataKey);
    Iterable<EventData> findEventDataByTriggerObject(String objKey);
    Iterable<EventData> findEventDataByTriggerType(String triggerType);
}
