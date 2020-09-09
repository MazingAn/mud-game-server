package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.HangupObject;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

import java.util.List;

public interface HangupObjectRepository extends SpecificationRepository<HangupObject, Long> {
    List<HangupObject> findHangupObjectByHangupType(String hangupType);
}
