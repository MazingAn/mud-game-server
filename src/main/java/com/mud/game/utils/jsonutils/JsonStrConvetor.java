package com.mud.game.utils.jsonutils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashSet;
import java.util.Set;

public class JsonStrConvetor {

    public static Set<String> ToSet(String jsonStr)  {

        Set<String> result = new HashSet<>();
        if(jsonStr.trim().equals("") ){
            return result;
        }

        try{
            ObjectMapper mapper = new ObjectMapper();
            result =  mapper.readValue(jsonStr, Set.class);
        }catch (Exception e){
            System.out.println("Json解析为Set失败， 源字符串为 " + jsonStr);
        }

        return result;
    }

}
