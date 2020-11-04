package com.mud.game.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "test",
        url = "http://localhost:8080")
public interface IWeixinFeign {
    @RequestMapping(method = RequestMethod.GET, consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    Object getAccessToken(@RequestParam String appid, @RequestParam String secret, @RequestParam String code);

    @RequestMapping(value = "/a", method = RequestMethod.GET)
    Object getA();
}
