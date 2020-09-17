package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.ObjectType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ObjectType")
@Api(tags = "物品类型管理接口")
public class ObjectTypeController {

    /**
     * 获取所有物品类型
     * */
    @GetMapping("/all")
    @ApiOperation("获取所有物品类型")
    public List<ObjectType> all(){
        Iterable<ObjectType>  iterable =  DbMapper.objectTypeRepository.findAll();
        List<ObjectType> _result = new ArrayList<ObjectType>();
        iterable.forEach(_result::add);
        _result.add(new ObjectType("EQUIPMENT", " 装备"));
        _result.add(new ObjectType("SKILL_BOOK", " 技能书"));
        _result.add(new ObjectType("GEM", " 宝石"));
        return _result;
    }

    /**
     * 获取所有物品类型
     * */
    @GetMapping("/normalObject")
    @ApiOperation("获取所有普通物品类型")
    public Iterable<ObjectType> allNormalObject(){
        return DbMapper.objectTypeRepository.findAll();
    }

    /**
     * 根据物品类型获得物品列表
     * */
    @GetMapping("/find/{typeKey}")
    @ApiOperation("根据typeKey查询对应物品")
    public Iterable<?> findObjectsByType(@PathVariable  String typeKey){
        switch (typeKey){
            case "EQUIPMENT":
                return DbMapper.equipmentRepository.findAll();
            case "SKILL_BOOK":
                return DbMapper.skillBookRepository.findAll();
            case "GEM":
                return DbMapper.gemRepository.findAll();
            default:
                return DbMapper.normalObjectRepository.findNormalObjectsByCategory(typeKey);
        }
    }


    /**
     * 查询ObjectType
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    public Page<ObjectType> query(@RequestParam(defaultValue="0") int page,
                               @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        return DbMapper.objectTypeRepository.findAll(paging);
    }

    /**
     * 修改物品类型
     *
     * @param updatedObjectType 更新的物品类型
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    public ObjectType editObjectType(@RequestBody ObjectType updatedObjectType, @PathVariable Long id) {
        updatedObjectType.setId(id);
        return DbMapper.objectTypeRepository.save(updatedObjectType);
    }

    /**
     * 删除物品类型
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteObjectType(@Valid @PathVariable Long id){
        DbMapper.objectTypeRepository.deleteById(id);
    }



}
