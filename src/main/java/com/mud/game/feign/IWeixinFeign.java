package com.mud.game.feign;

import org.json.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "weixin",
        url = "https://api.weixin.qq.com")
// url = "http://localhost:8080")
public interface IWeixinFeign {
    /**
     * 获取token
     *
     * @param appid      应用唯一标识，在微信开放平台提交应用审核通过后获得
     * @param secret     应用密钥AppSecret，在微信开放平台提交应用审核通过后获得
     * @param code       填写第一步获取的code参数
     * @param grant_type 填authorization_code
     * @return
     */
    @RequestMapping(value = "/sns/oauth2/access_token", method = RequestMethod.GET, consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    JSONObject getAccessToken(@RequestParam String appid, @RequestParam String secret, @RequestParam String code, @RequestParam String grant_type);

    /**
     * 刷新token
     *
     * @param appid         应用唯一标识
     * @param refresh_token 填写通过access_token获取到的refresh_token参数
     * @param grant_type    填refresh_token
     * @return
     */
    @RequestMapping(value = "/sns/oauth2/refresh_token", method = RequestMethod.GET, consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    JSONObject refreshToken(@RequestParam String appid, @RequestParam String refresh_token, @RequestParam String grant_type);

    /**
     * 检验授权凭证（access_token）是否有效
     *
     * @param access_token 调用接口凭证
     * @param openid       普通用户标识，对该公众帐号唯一
     * @return
     */
    @RequestMapping(value = "/sns/auth", method = RequestMethod.GET, consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    JSONObject auth(@RequestParam String access_token, @RequestParam String openid);

    /**
     * 获取用户个人信息（UnionID机制）
     *
     * @param access_token 调用凭证
     * @param openid       普通用户的标识，对当前开发者帐号唯一
     * @return
     */
    @RequestMapping(value = "/sns/userinfo", method = RequestMethod.GET)
    JSONObject userinfo(@RequestParam String access_token, @RequestParam String openid);

    @RequestMapping(value = "/a", method = RequestMethod.GET)
    JSONObject getA(@RequestParam String appid, @RequestParam String secret, @RequestParam String code, @RequestParam String grant_type);
}
