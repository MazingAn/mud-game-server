package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.SlotMaterial;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * 开孔材料Controller
 * 开孔材料的增删改查
 */
@RestController
@RequestMapping("/SlotMaterial")
@Api(tags = "开孔材料")
public class SlotMaterialController {
    /**
     * 增加开孔材料
     *
     * @param slotMaterial 表单提交的开孔材料
     * @return 保存后的开孔材料
     */
    @PostMapping("/add")
    public SlotMaterial addNpcBoundItem(@Valid @RequestBody SlotMaterial slotMaterial) {
        return DbMapper.slotMaterialRepository.save(slotMaterial);
    }

    /**
     * 查询开孔材料
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     */
    @GetMapping("")
    public Page<SlotMaterial> query(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "20") int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<SlotMaterial> pageResult = DbMapper.slotMaterialRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 查询开孔材料
     */
    @GetMapping("all")
    public Iterable<SlotMaterial> all() {
        return DbMapper.slotMaterialRepository.findAll();
    }

    /**
     * 修改开孔材料
     *
     * @param slotMaterial 更新的开孔材料
     * @param id           要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    public SlotMaterial editnpcSlotMaterial(@RequestBody SlotMaterial slotMaterial, @PathVariable Long id) {
        slotMaterial.setId(id);
        return DbMapper.slotMaterialRepository.save(slotMaterial);
    }

    /**
     * 删除开孔材料
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     */
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteSlotMaterial(@Valid @PathVariable Long id) {
        DbMapper.slotMaterialRepository.deleteById(id);
    }


}
