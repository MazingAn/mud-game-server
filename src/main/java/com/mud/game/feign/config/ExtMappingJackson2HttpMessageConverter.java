package com.mud.game.feign.config;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

public class ExtMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
    /*解决no suitable HttpMessageConverter found for response type [class com.XXX] and content type [text/plain]*/
    public ExtMappingJackson2HttpMessageConverter() {
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.TEXT_PLAIN);
        setSupportedMediaTypes(mediaTypes);// tag6
    }
}
