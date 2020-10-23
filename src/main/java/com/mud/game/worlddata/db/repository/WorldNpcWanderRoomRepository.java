package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.WorldNpcWanderRoom;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

import java.util.List;

public interface WorldNpcWanderRoomRepository extends SpecificationRepository<WorldNpcWanderRoom, Long> {
    List<WorldNpcWanderRoom> findListWorldNpcWanderRoomByNpcDataKey(String dataKey);

    WorldNpcWanderRoom findWorldNpcWanderRoomByNpcDataKeyAndRoomDataKey(String dataKey, String location);
}
