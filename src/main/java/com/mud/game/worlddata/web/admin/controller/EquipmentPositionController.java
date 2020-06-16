package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.EquipmentPosition;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;


/**
 * 游戏区域Controller
 * 提供EquipmentPosition的增删改查
 * */
@RestController
@RequestMapping("/EquipmentPosition")
@Api(tags = "装备位置管理接口")
public class EquipmentPositionController {
    /**
     * 增加EquipmentPosition
     *
     * @param newEquipmentPosition 表单提交的EquipmentPosition
     *
     * @return 保存后的EquipmentPosition信息
     * */
    @PostMapping("/add")
    public EquipmentPosition addEquipmentPosition(@Valid  @RequestBody EquipmentPosition newEquipmentPosition) {
        return DbMapper.equipmentPositionRepository.save(newEquipmentPosition);
    }

    /**
     * 查询EquipmentPosition
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    public Page<EquipmentPosition> query(@RequestParam(defaultValue="0") int page,
                                   @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<EquipmentPosition> pageResult = DbMapper.equipmentPositionRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 修改游戏设置
     *
     * @param updatedEquipmentPosition 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    public EquipmentPosition editEquipmentPosition(@RequestBody EquipmentPosition updatedEquipmentPosition, @PathVariable Long id) {
        updatedEquipmentPosition.setId(id);
        return DbMapper.equipmentPositionRepository.save(updatedEquipmentPosition);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteEquipmentPosition(@Valid @PathVariable Long id){
        DbMapper.equipmentPositionRepository.deleteById(id);
    }

}
