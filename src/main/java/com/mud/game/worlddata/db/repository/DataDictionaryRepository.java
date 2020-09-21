package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.DataDictionary;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

public interface DataDictionaryRepository extends SpecificationRepository<DataDictionary, Long> {
    DataDictionary findDataDictionaryByDataKey(String discard_contents);
}
