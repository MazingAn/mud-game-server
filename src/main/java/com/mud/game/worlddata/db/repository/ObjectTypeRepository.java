package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.ObjectType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ObjectTypeRepository extends PagingAndSortingRepository<ObjectType, Long> {

}
