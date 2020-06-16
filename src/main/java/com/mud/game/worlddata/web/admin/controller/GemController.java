package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.Gem;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;


/**
 * 游戏区域Controller
 * 提供Gem的增删改查
 * */
@RestController
@RequestMapping("/Gem")
@Api(tags = "宝石管理接口")
public class GemController {
    /**
     * 增加Gem
     *
     * @param newGem 表单提交的Gem
     *
     * @return 保存后的Gem信息
     * */
    @PostMapping("/add")
    public Gem addGem(@Valid  @RequestBody Gem newGem) {
        return DbMapper.gemRepository.save(newGem);
    }

    /**
     * 查询Gem
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    public Page<Gem> query(@RequestParam(defaultValue="0") int page,
                                   @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<Gem> pageResult = DbMapper.gemRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 修改游戏设置
     *
     * @param updatedGem 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    public Gem editGem(@RequestBody Gem updatedGem, @PathVariable Long id) {
        updatedGem.setId(id);
        return DbMapper.gemRepository.save(updatedGem);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteGem(@Valid @PathVariable Long id){
        DbMapper.gemRepository.deleteById(id);
    }

}
