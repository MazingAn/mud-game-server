package com.mud.game.worlddata.web.admin.controller;


import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.HangupObject;
import com.mud.game.worlddata.db.models.HangupType;
import com.mud.game.worlddata.db.models.HangupType;
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
@RequestMapping("/HangupType")
@Api(tags = "挂机类型接口")
public class HangupTypeController {

    /**
     * 获取所有挂机类型
     */
    @GetMapping("/all")
    @ApiOperation("获取所有挂机类型")
    public Iterable<HangupType> all() {
        return DbMapper.hangupTypeRepository.findAll();
    }


    /**
     * 查询挂机类型
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    @ApiOperation("分页获取挂机类型")
    public Page<HangupType> query(@RequestParam(defaultValue="0") int page,
                                  @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        return DbMapper.hangupTypeRepository.findAll(paging);
    }

    /**
     * 修改物品类型
     *
     * @param updatedHangupType 更新的物品类型
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    @ApiOperation("编辑挂机类型")
    public HangupType editHangupType(@RequestBody HangupType updatedHangupType, @PathVariable Long id) {
        updatedHangupType.setId(id);
        return DbMapper.hangupTypeRepository.save(updatedHangupType);
    }

    /**
     * 删除物品类型
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     * */
    @DeleteMapping("/{id}")
    @Transactional
    @ApiOperation("删除挂机类型")
    public void deleteHangupType(@Valid @PathVariable Long id){
        DbMapper.hangupTypeRepository.deleteById(id);
    }
}
