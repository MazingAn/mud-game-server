package com.mud.game.feign;

import com.mud.game.object.account.Player;
import org.json.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(
        name = "record",
        url = "http://127.0.0.1:8089/")
public interface IRecordFeign {
    /**
     * 查询分区
     *
     * @param gameKey
     * @return
     */
    @RequestMapping(value = "divide/getDivideByGameKey", method = RequestMethod.GET, consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    Map<String, Object> getDivideByGameKey(@RequestParam("gameKey") String gameKey);

    /**
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping(value = "player/addPlayer", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    Map<String, Object> addPlayer(@RequestParam("userName") String userName, @RequestParam("password") String password);

    @RequestMapping(value = "player/playerLogin", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    Map<String, Object> playerLogin(@RequestParam("userName") String userName, @RequestParam("password") String password);
}
