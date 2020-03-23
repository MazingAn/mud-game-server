package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.WorldExit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface WorldExitRepository extends PagingAndSortingRepository<WorldExit, Long> {
    WorldExit deleteWorldExitById(Long id);
}
