package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.events.EventTriggerType;
import com.mud.game.handler.EventActionTypeHandler;
import com.mud.game.handler.EventTriggerTypeHandler;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.WeaponType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;


/**
 * 游戏区域Controller
 * 提供WeaponType的增删改查
 * */
@RestController
@RequestMapping("/WeaponType")
@Api(tags = "D:事件管理接口")
public class WeaponTypeController {
    /**
     * 增加WeaponType
     *
     * @param newWeaponType 表单提交的WeaponType
     *
     * @return 保存后的WeaponType信息
     * */
    @PostMapping("/add")
    @ApiOperation("增加事件绑定")
    public WeaponType addWeaponType(@Valid  @RequestBody WeaponType newWeaponType) {
        return DbMapper.weaponTypeRepository.save(newWeaponType);
    }

    /**
     * 查询WeaponType
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    @ApiOperation("分页显示事件绑定")
    public Page<WeaponType> query(@RequestParam(defaultValue="0") int page,
                                         @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<WeaponType> pageResult = DbMapper.weaponTypeRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 获取所有weaponType
     * */
    @GetMapping("/all")
    @ApiOperation("获取所有weaponType")
    public Iterable<WeaponType> all(){
        return DbMapper.weaponTypeRepository.findAll();
    }

    /**
     * 修改游戏设置
     *
     * @param updatedWeaponType 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    @ApiOperation("编辑事件绑定")
    public WeaponType editWeaponType(@RequestBody WeaponType updatedWeaponType, @PathVariable Long id) {
        updatedWeaponType.setId(id);
        return DbMapper.weaponTypeRepository.save(updatedWeaponType);
    }

    /**
     * 删除游戏设置
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @ApiOperation("删除事件绑定")
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteWeaponType(@Valid @PathVariable Long id){
        DbMapper.weaponTypeRepository.deleteById(id);
    }

}
