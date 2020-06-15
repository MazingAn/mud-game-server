package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.HangupObject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface HangupObjectRepository extends PagingAndSortingRepository<HangupObject, Long> {
}
