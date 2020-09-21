package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.CompositeMaterial;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * 合成材料Controller
 * 合成材料的增删改查
 */
@RestController
@RequestMapping("/CompositeMaterial")
@Api(tags = "合成材料")
public class CompositeMaterialController {
    /**
     * 增加合成材料
     *
     * @param CompositeMaterial 表单提交的合成材料
     * @return 保存后的合成材料
     */
    @PostMapping("/add")
    public CompositeMaterial addNpcBoundItem(@Valid @RequestBody CompositeMaterial CompositeMaterial) {
        return DbMapper.compositeMaterialRepository.save(CompositeMaterial);
    }

    /**
     * 查询合成材料
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     */
    @GetMapping("")
    public Page<CompositeMaterial> query(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "20") int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<CompositeMaterial> pageResult = DbMapper.compositeMaterialRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 查询合成材料
     */
    @GetMapping("all")
    public Iterable<CompositeMaterial> all() {
        return DbMapper.compositeMaterialRepository.findAll();
    }

    /**
     * 修改合成材料
     *
     * @param CompositeMaterial 更新的合成材料
     * @param id                要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    public CompositeMaterial editCompositeMaterial(@RequestBody CompositeMaterial CompositeMaterial, @PathVariable Long id) {
        CompositeMaterial.setId(id);
        return DbMapper.compositeMaterialRepository.save(CompositeMaterial);
    }

    /**
     * 删除合成材料
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     */
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteCompositeMaterial(@Valid @PathVariable Long id) {
        DbMapper.compositeMaterialRepository.deleteById(id);
    }

}
