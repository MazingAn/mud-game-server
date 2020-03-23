package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.WorldRoom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface WorldRoomRepository extends PagingAndSortingRepository<WorldRoom, Long> {
    WorldRoom deleteWorldRoomById(Long id);
}
