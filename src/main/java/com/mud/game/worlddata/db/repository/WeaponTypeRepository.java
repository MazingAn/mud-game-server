package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.WeaponType;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface WeaponTypeRepository extends PagingAndSortingRepository<WeaponType, Long> {
}
