package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.NpcShop;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;


/**
 * 游戏区域Controller
 * 提供NpcShop的增删改查
 * */
@RestController
@RequestMapping("/NpcShop")
@Api(tags = "NPC商店绑定管理接口")
public class NpcShopController {
    /**
     * 增加NpcShop
     *
     * @param newNpcShop 表单提交的NpcShop
     *
     * @return 保存后的NpcShop信息
     * */
    @PostMapping("/add")
    public NpcShop addNpcShop(@Valid  @RequestBody NpcShop newNpcShop) {
        return DbMapper.npcShopRepository.save(newNpcShop);
    }

    /**
     * 查询NpcShop
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    public Page<NpcShop> query(@RequestParam(defaultValue="0") int page,
                                   @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<NpcShop> pageResult = DbMapper.npcShopRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 修改游戏设置
     *
     * @param updatedNpcShop 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    public NpcShop editNpcShop(@RequestBody NpcShop updatedNpcShop, @PathVariable Long id) {
        updatedNpcShop.setId(id);
        return DbMapper.npcShopRepository.save(updatedNpcShop);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteNpcShop(@Valid @PathVariable Long id){
        DbMapper.npcShopRepository.deleteById(id);
    }

}
