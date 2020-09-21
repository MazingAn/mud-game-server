package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.QualityMaterial;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * 进阶材料Controller
 * 进阶材料的增删改查
 */
@RestController
@RequestMapping("/QualityMaterial")
@Api(tags = "进阶材料")
public class QualityMaterialController {
    /**
     * 增加进阶材料
     *
     * @param QualityMaterial 表单提交的进阶材料
     * @return 保存后的进阶材料
     */
    @PostMapping("/add")
    public QualityMaterial addNpcBoundItem(@Valid @RequestBody QualityMaterial QualityMaterial) {
        return DbMapper.qualityMaterialRepository.save(QualityMaterial);
    }

    /**
     * 查询进阶材料
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     */
    @GetMapping("")
    public Page<QualityMaterial> query(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "20") int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<QualityMaterial> pageResult = DbMapper.qualityMaterialRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 查询进阶材料
     */
    @GetMapping("all")
    public Iterable<QualityMaterial> all() {
        return DbMapper.qualityMaterialRepository.findAll();
    }

    /**
     * 修改进阶材料
     *
     * @param QualityMaterial 更新的进阶材料
     * @param id                 要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    public QualityMaterial editQualityMaterial(@RequestBody QualityMaterial QualityMaterial, @PathVariable Long id) {
        QualityMaterial.setId(id);
        return DbMapper.qualityMaterialRepository.save(QualityMaterial);
    }

    /**
     * 删除进阶材料
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     */
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteQualityMaterial(@Valid @PathVariable Long id) {
        DbMapper.qualityMaterialRepository.deleteById(id);
    }
}
