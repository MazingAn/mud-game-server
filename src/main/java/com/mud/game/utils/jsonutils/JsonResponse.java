package com.mud.game.utils.jsonutils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonResponse {
    public static String JsonStringResponse(Object obj) {

        ObjectMapper mapper = new ObjectMapper();
        try{
            return mapper.writeValueAsString(obj);
        }catch (Exception JsonProcessingException){
            return null;
        }
    }
}
