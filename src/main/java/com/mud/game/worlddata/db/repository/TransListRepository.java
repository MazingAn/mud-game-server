package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.ActionAcceptQuest;
import com.mud.game.worlddata.db.models.TransList;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransListRepository extends SpecificationRepository<TransList, Long> {
    Iterable<TransList> findTransListByNpc(String npcKey);
    TransList findTransListByNpcAndRoom(String npcKey, String roomKey);
}
