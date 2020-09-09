package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.PlayerTitle;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

import java.util.List;
import java.util.Set;

public interface PlayerTitleRepository extends SpecificationRepository<PlayerTitle, Long> {
    PlayerTitle findPlayerTitleByDataKey(String dataKey);

    List<PlayerTitle> findPlayerTitleByDataKeyIn(Set<String> titleKeySet);
}
