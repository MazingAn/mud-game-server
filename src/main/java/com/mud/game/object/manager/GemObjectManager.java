package com.mud.game.object.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.object.typeclass.GemObject;
import com.mud.game.utils.jsonutils.Attr2Map;
import com.mud.game.utils.jsonutils.JsonStrConvetor;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.Equipment;
import com.mud.game.worlddata.db.models.Gem;

import java.util.ArrayList;

public class GemObjectManager {
    public static GemObject create(String templateKey) throws JsonProcessingException {
        GemObject gemObject = new GemObject();
        Gem template = DbMapper.gemRepository.findGemByDataKey(templateKey);
        gemObject.setDataKey(template.getDataKey());
        gemObject.setName(template.getName());
        gemObject.setDescription(template.getDescription());
        gemObject.setUnitName(template.getUnitName());
        gemObject.setCategory(template.getCategory());
        gemObject.setUnique(template.isUniqueInBag());
        gemObject.setMaxStack(template.getMaxStack());
        gemObject.setCanDiscard(template.isCanDiscard());
        gemObject.setCanRemove(template.isCanRemove());
        gemObject.setIcon(template.getIcon());
        gemObject.setQuality(template.getQuality());
        gemObject.setOwner(null);
        gemObject.setTotalNumber(0);
        gemObject.setAttrs(Attr2Map.equipmentAttrTrans(template.getAttrs()));
        return gemObject;
    }
}
