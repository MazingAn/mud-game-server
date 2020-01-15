package com.mud.game.utils.jsonutils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashSet;
import java.util.Set;

public class JsonStrConvetor {

    public static Set ToSet(String jsonStr) throws JsonProcessingException {
        if(jsonStr.trim().equals("")){
            return new HashSet<>();
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonStr, Set.class);
    }

}
