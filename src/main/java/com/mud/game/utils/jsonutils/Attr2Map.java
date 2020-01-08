package com.mud.game.utils.jsonutils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class Attr2Map {

    public static Map<String, Map<String, Object>> transform(String attr) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(attr, new TypeReference<Map < String, Map < String, Object>>>() {} );
    }
}
