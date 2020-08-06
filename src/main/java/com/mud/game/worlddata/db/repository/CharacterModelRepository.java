package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.CharacterModel;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

public interface CharacterModelRepository extends SpecificationRepository<CharacterModel, Long> {
    CharacterModel findCharacterModelByDataKey(String dataKey);
}
