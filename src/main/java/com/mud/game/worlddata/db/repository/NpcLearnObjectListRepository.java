package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.NormalObject;
import com.mud.game.worlddata.db.models.NpcLearnObjectList;
import org.springframework.data.repository.CrudRepository;

public interface NpcLearnObjectListRepository extends CrudRepository<NpcLearnObjectList, Long> {
    NpcLearnObjectList getByNpcKey(String npcKey);
}
