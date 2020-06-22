package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.EventData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;


/**
 * 游戏区域Controller
 * 提供EventData的增删改查
 * */
@RestController
@RequestMapping("/EventData")
@Api(tags = "D:事件管理接口")
public class EventDataController {
    /**
     * 增加EventData
     *
     * @param newEventData 表单提交的EventData
     *
     * @return 保存后的EventData信息
     * */
    @PostMapping("/add")
    @ApiOperation("增加事件绑定")
    public EventData addEventData(@Valid  @RequestBody EventData newEventData) {
        return DbMapper.eventDataRepository.save(newEventData);
    }

    /**
     * 查询EventData
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     * */
    @GetMapping("")
    @ApiOperation("分页显示事件绑定")
    public Page<EventData> query(@RequestParam(defaultValue="0") int page,
                                         @RequestParam(defaultValue="20") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<EventData> pageResult = DbMapper.eventDataRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 修改游戏设置
     *
     * @param updatedEventData 更新的游戏设置
     * @param id  要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    @ApiOperation("编辑事件绑定")
    public EventData editEventData(@RequestBody EventData updatedEventData, @PathVariable Long id) {
        updatedEventData.setId(id);
        return DbMapper.eventDataRepository.save(updatedEventData);
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
    public void deleteEventData(@Valid @PathVariable Long id){
        DbMapper.eventDataRepository.deleteById(id);
    }

}
