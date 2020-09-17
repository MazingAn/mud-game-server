package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.ObjectBindPrice;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * 游戏区域Controller
 * 提供ObjectBindPrice的增删改查
 */
@RestController
@RequestMapping("/ObjectBindPrice")
@Api(tags = "物品价格出售管理接口")
public class ObjectBindPriceController {
    /**
     * 增加物品价格
     *
     * @param ObjectBindPrice 表单提交的ObjectBindPrice
     * @return 保存后的物品价格信息
     */
    @PostMapping("/add")
    public ObjectBindPrice addObjectBindPrice(@Valid @RequestBody ObjectBindPrice ObjectBindPrice) {
        return DbMapper.objectBindPriceRepository.save(ObjectBindPrice);
    }

    /**
     * 查询物品价格
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     */
    @GetMapping("")
    public Page<ObjectBindPrice> query(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "20") int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<ObjectBindPrice> pageResult = DbMapper.objectBindPriceRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 查询所有商店
     */
    @GetMapping("all")
    public Iterable<ObjectBindPrice> all() {
        return DbMapper.objectBindPriceRepository.findAll();
    }

    /**
     * 修改游戏设置
     *
     * @param updated物品价格 更新的游戏设置
     * @param id          要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    public ObjectBindPrice edit物品价格(@RequestBody ObjectBindPrice updated物品价格, @PathVariable Long id) {
        updated物品价格.setId(id);
        return DbMapper.objectBindPriceRepository.save(updated物品价格);
    }

    /**
     * 删除物品价格
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     */
    @DeleteMapping("/{id}")
    @Transactional
    public void delete物品价格(@Valid @PathVariable Long id) {
        DbMapper.objectBindPriceRepository.deleteById(id);
    }
}
