package com.mud.game.feign.FeignService;

import com.mud.game.feign.IWeixinFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WeixinService<F, S> {
    /*
     * 这是所有的仓库映射
     * 仓库会在这里被全部自动实现并开放出去
     * 为了绕开Spring boot 不能对全局静态变量进行@Autowried操作，所有仓库的@AutoWried动作都放在了Setter方法内
     * */

    public static IWeixinFeign weixinFeign;

    @Autowired
    public void setWeixinFeign(IWeixinFeign weixinFeign) {
        WeixinService.weixinFeign = weixinFeign;
    }
}

