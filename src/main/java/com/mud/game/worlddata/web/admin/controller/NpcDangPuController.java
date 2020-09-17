package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.NpcDangPu;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * 游戏区域Controller
 * 提供NpcDangPu的增删改查
 */
@RestController
@RequestMapping("/NpcDangPu")
@Api(tags = "npc出售权限管理接口")
public class NpcDangPuController {
    /**
     * 增加npc出售权限
     *
     * @param npcDangPu 表单提交的npcDangPu
     * @return 保存后的npc出售权限信息
     */
    @PostMapping("/add")
    public NpcDangPu addNpcDangPu(@Valid @RequestBody NpcDangPu npcDangPu) {
        return DbMapper.npcDangPuRepository.save(npcDangPu);
    }

    /**
     * 查询npc出售权限
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     */
    @GetMapping("")
    public Page<NpcDangPu> query(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "20") int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<NpcDangPu> pageResult = DbMapper.npcDangPuRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 查询所有商店
     */
    @GetMapping("all")
    public Iterable<NpcDangPu> all() {
        return DbMapper.npcDangPuRepository.findAll();
    }

    /**
     * 修改游戏设置
     *
     * @param npcDangPu 更新的游戏设置
     * @param id             要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    public NpcDangPu editnpc出售权限(@RequestBody NpcDangPu npcDangPu, @PathVariable Long id) {
        npcDangPu.setId(id);
        return DbMapper.npcDangPuRepository.save(npcDangPu);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     */
    @DeleteMapping("/{id}")
    @Transactional
    public void deletenpc出售权限(@Valid @PathVariable Long id) {
        DbMapper.npcDangPuRepository.deleteById(id);
    }
}
