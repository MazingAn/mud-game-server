package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;


/**
 * 游戏区域Controller
 * 提供Shop的增删改查
 * */
@RestController
@RequestMapping("/Shop")
public class ShopController {
    /**
     * 增加Shop
     *
     * @param newShop 表单提交的Shop
     *
     * @return 保存后的Shop信息
     * */
    @PostMapping("/add")
    public Shop addShop(@Valid  @RequestBody Shop newShop) {
        return DbMapper.shopRepository.save(newShop);
    }

    /**
     * 查询Shop
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    public Page<Shop> query(@RequestParam(defaultValue="0") int page,
                                   @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<Shop> pageResult = DbMapper.shopRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 修改游戏设置
     *
     * @param updatedShop 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    public Shop editShop(@RequestBody Shop updatedShop, @PathVariable Long id) {
        updatedShop.setId(id);
        return DbMapper.shopRepository.save(updatedShop);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteShop(@Valid @PathVariable Long id){
        DbMapper.shopRepository.deleteById(id);
    }

}
