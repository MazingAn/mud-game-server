package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.CharacterModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CharacterModelRepository extends PagingAndSortingRepository<CharacterModel, Long> {
    CharacterModel findCharacterModelByDataKey(String dataKey);
}
