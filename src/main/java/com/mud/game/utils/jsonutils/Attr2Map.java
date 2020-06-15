package com.mud.game.utils.jsonutils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class Attr2Map {

    public static Map<String, Map<String, Object>> characterAttrTrans(String attr)  {
        ObjectMapper mapper = new ObjectMapper();
        try{
            return mapper.readValue(attr, new TypeReference<Map < String, Map < String, Object>>>() {} );
        }catch (JsonProcessingException e){
            System.out.println("严重错误！---》无法解析角色属性字符串" + attr);
            return new HashMap<>();
        }
    }

    public static Map<String, Map<String, Object>> equipmentAttrTrans(String attr)  {
        ObjectMapper mapper = new ObjectMapper();
        try{
            return mapper.readValue(attr, new TypeReference<Map < String, Map < String, Object>>>() {} );
        }catch (JsonProcessingException e){
            System.out.println("严重错误！---》无法解析装备属性字符串" + attr);
            return new HashMap<>();
        }
    }
}
