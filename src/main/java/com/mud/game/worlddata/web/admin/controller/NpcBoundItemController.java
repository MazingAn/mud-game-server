package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.NpcBoundItem;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * npc掉落物品绑定Controller
 * npc掉落物品绑定的增删改查
 */
@RestController
@RequestMapping("/NpcBoundItem")
@Api(tags = "npc掉落物品绑定")
public class NpcBoundItemController {
    /**
     * 增加npc掉落物品绑定
     *
     * @param npcBoundItem 表单提交的npc掉落物品绑定
     * @return 保存后的npc掉落物品绑定
     */
    @PostMapping("/add")
    public NpcBoundItem addNpcBoundItem(@Valid @RequestBody NpcBoundItem npcBoundItem) {
        return DbMapper.npcBoundItemRepository.save(npcBoundItem);
    }

    /**
     * 查询npc掉落物品绑定
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     */
    @GetMapping("")
    public Page<NpcBoundItem> query(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "20") int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<NpcBoundItem> pageResult = DbMapper.npcBoundItemRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 查询所有商店
     */
    @GetMapping("all")
    public Iterable<NpcBoundItem> all() {
        return DbMapper.npcBoundItemRepository.findAll();
    }

    /**
     * 修改npc掉落物品绑定
     *
     * @param npcBoundItem 更新的npc掉落物品绑定
     * @param id           要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    public NpcBoundItem editnpcNpcBoundItem(@RequestBody NpcBoundItem npcBoundItem, @PathVariable Long id) {
        npcBoundItem.setId(id);
        return DbMapper.npcBoundItemRepository.save(npcBoundItem);
    }

    /**
     * 删除npc掉落物品绑定
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     */
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteNpcBoundItem(@Valid @PathVariable Long id) {
        DbMapper.npcBoundItemRepository.deleteById(id);
    }


}
