package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.Equipment;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;


/**
 * 游戏区域Controller
 * 提供Equipment的增删改查
 * */
@RestController
@RequestMapping("/Equipment")
@Api(tags = "装备管理接口")
public class EquipmentController {
    /**
     * 增加Equipment
     *
     * @param newEquipment 表单提交的Equipment
     *
     * @return 保存后的Equipment信息
     * */
    @PostMapping("/add")
    public Equipment addEquipment(@Valid  @RequestBody Equipment newEquipment) {
        return DbMapper.equipmentRepository.save(newEquipment);
    }

    /**
     * 查询Equipment
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    public Page<Equipment> query(@RequestParam(defaultValue="0") int page,
                                   @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<Equipment> pageResult = DbMapper.equipmentRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 修改游戏设置
     *
     * @param updatedEquipment 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    public Equipment editEquipment(@RequestBody Equipment updatedEquipment, @PathVariable Long id) {
        updatedEquipment.setId(id);
        return DbMapper.equipmentRepository.save(updatedEquipment);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteEquipment(@Valid @PathVariable Long id){
        DbMapper.equipmentRepository.deleteById(id);
    }

}
