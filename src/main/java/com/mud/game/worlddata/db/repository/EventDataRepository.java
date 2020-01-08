package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.CharacterModel;
import com.mud.game.worlddata.db.models.EventData;
import org.springframework.data.repository.CrudRepository;

public interface EventDataRepository extends CrudRepository<EventData, Long> {
    public EventData findEventDataById(Long id);
    public EventData findEventDataByDataKey(String dataKey);
    public Iterable<EventData> findEventDataByTriggerObject(String objKey);
}
