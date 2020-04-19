package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.CharacterModel;
import org.springframework.data.repository.CrudRepository;

public interface CharacterModelRepository extends CrudRepository<CharacterModel, Long> {
    CharacterModel findCharacterModelByDataKey(String dataKey);
}
