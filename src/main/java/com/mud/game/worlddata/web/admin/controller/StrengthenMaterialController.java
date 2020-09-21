package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.StrengthenMaterial;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * 强化材料Controller
 * 强化材料的增删改查
 */
@RestController
@RequestMapping("/StrengthenMaterial")
@Api(tags = "强化材料")
public class StrengthenMaterialController {
    /**
     * 增加强化材料
     *
     * @param StrengthenMaterial 表单提交的强化材料
     * @return 保存后的强化材料
     */
    @PostMapping("/add")
    public StrengthenMaterial addNpcBoundItem(@Valid @RequestBody StrengthenMaterial StrengthenMaterial) {
        return DbMapper.strengthenMaterialRepository.save(StrengthenMaterial);
    }

    /**
     * 查询强化材料
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     */
    @GetMapping("")
    public Page<StrengthenMaterial> query(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "20") int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<StrengthenMaterial> pageResult = DbMapper.strengthenMaterialRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 查询强化材料
     */
    @GetMapping("all")
    public Iterable<StrengthenMaterial> all() {
        return DbMapper.strengthenMaterialRepository.findAll();
    }

    /**
     * 修改强化材料
     *
     * @param StrengthenMaterial 更新的强化材料
     * @param id                 要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    public StrengthenMaterial editStrengthenMaterial(@RequestBody StrengthenMaterial StrengthenMaterial, @PathVariable Long id) {
        StrengthenMaterial.setId(id);
        return DbMapper.strengthenMaterialRepository.save(StrengthenMaterial);
    }

    /**
     * 删除强化材料
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     */
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteStrengthenMaterial(@Valid @PathVariable Long id) {
        DbMapper.strengthenMaterialRepository.deleteById(id);
    }

}
