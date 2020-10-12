package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.models.OrderInfo;
import com.mud.game.worlddata.db.repository.OrderInfoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/OrderInfo")
@Api(tags = "订单管理接口")
public class OrderInfoController {
    @Autowired
    private OrderInfoRepository orderInfoRepository;

    /**
     * 增加订单信息
     */
    @GetMapping("/addOrderInfo")
    @ApiOperation("增加订单信息")
    public OrderInfo addOrderInfo(@RequestBody OrderInfo orderInfo) {
        if (null == orderInfo) {
            return null;
        }
        return orderInfoRepository.save(orderInfo);
    }

    /**
     * 分页查询订单信息
     */
    @GetMapping("/findPageOrderInfo")
    @ApiOperation("增加订单信息")
    public Page<OrderInfo> findPageOrderInfo(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "20") int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<OrderInfo> pageResult = orderInfoRepository.findAll(paging);
        return pageResult;
    }
}
