package com.mud.game.handler;

import com.mud.game.object.supertypeclass.CommonObject;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.NormalObject;
import org.apache.commons.collections4.map.HashedMap;

import java.util.Map;

public class UnitHandler {

    public static Map<String, String> unitsDataKey2NameMap = new HashedMap<>();
    public static Map<String, String> unitsName2DataKeyMap = new HashedMap<>();

    public static void initUnits(){
        Iterable<NormalObject> objects = DbMapper.normalObjectRepository.findNormalObjectsByCategory("TYPE_OBJECTTYPE_HUOBI");
        for(NormalObject object: objects){
            unitsDataKey2NameMap.put(object.getDataKey(),object.getName());
            unitsName2DataKeyMap.put(object.getName(), object.getDataKey());
        }
    }

    public static String convertUnitName(String unitName){
        return unitsName2DataKeyMap.get(unitName);
    }

    public static String convertUnitKey(String unitKey){
        return unitsDataKey2NameMap.get(unitKey);
    }

}
