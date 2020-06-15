package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.ShopGoods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;


/**
 * 游戏区域Controller
 * 提供ShopGoods的增删改查
 * */
@RestController
@RequestMapping("/ShopGoods")
public class ShopGoodsController {
    /**
     * 增加ShopGoods
     *
     * @param newShopGoods 表单提交的ShopGoods
     *
     * @return 保存后的ShopGoods信息
     * */
    @PostMapping("/add")
    public ShopGoods addShopGoods(@Valid  @RequestBody ShopGoods newShopGoods) {
        return DbMapper.shopGoodsRepository.save(newShopGoods);
    }

    /**
     * 查询ShopGoods
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    public Page<ShopGoods> query(@RequestParam(defaultValue="0") int page,
                                   @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<ShopGoods> pageResult = DbMapper.shopGoodsRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 修改游戏设置
     *
     * @param updatedShopGoods 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    public ShopGoods editShopGoods(@RequestBody ShopGoods updatedShopGoods, @PathVariable Long id) {
        updatedShopGoods.setId(id);
        return DbMapper.shopGoodsRepository.save(updatedShopGoods);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteShopGoods(@Valid @PathVariable Long id){
        DbMapper.shopGoodsRepository.deleteById(id);
    }

}
