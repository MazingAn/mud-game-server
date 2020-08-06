package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.WorldRoom;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

public interface WorldRoomRepository extends SpecificationRepository<WorldRoom, Long> {
    WorldRoom deleteWorldRoomById(Long id);
    WorldRoom findWorldRoomByDataKey(String dataKey);
    Iterable<WorldRoom> findWorldRoomsByLocation(String area);
}
