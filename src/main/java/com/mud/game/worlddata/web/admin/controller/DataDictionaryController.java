package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.constant.PostConstructConstant;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.DataDictionary;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * 数据字典Controller
 * 数据字典的增删改查
 */
@RestController
@RequestMapping("/DataDictionary")
@Api(tags = "数据字典")
public class DataDictionaryController {
    /**
     * 增加数据字典
     *
     * @param DataDictionary 表单提交的数据字典
     * @return 保存后的数据字典
     */
    @PostMapping("/add")
    public DataDictionary addNpcBoundItem(@Valid @RequestBody DataDictionary DataDictionary) {
        return DbMapper.dataDictionaryRepository.save(DataDictionary);
    }

    /**
     * 查询数据字典
     *
     * @param page 请求页码
     * @param size 每页展示的数量
     * @return 分页信息和页面内容
     */
    @GetMapping("")
    public Page<DataDictionary> query(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "20") int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<DataDictionary> pageResult = DbMapper.dataDictionaryRepository.findAll(paging);
        return pageResult;
    }

    /**
     * 查询数据字典
     */
    @GetMapping("all")
    public Iterable<DataDictionary> all() {
        return DbMapper.dataDictionaryRepository.findAll();
    }

    /**
     * 修改数据字典
     *
     * @param DataDictionary 更新的数据字典
     * @param id             要更新的行的id
     * @return 更新后信息内容
     */
    @PutMapping("/{id}")
    public DataDictionary editDataDictionary(@RequestBody DataDictionary DataDictionary, @PathVariable Long id) {
        DataDictionary.setId(id);
        DbMapper.dataDictionaryRepository.save(DataDictionary);
        PostConstructConstant.overload();
        return DataDictionary;
    }

    /**
     * 删除数据字典
     *
     * @param id 要删除的行的ID
     * @return 删除的信息内容
     */
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteDataDictionary(@Valid @PathVariable Long id) {
        DbMapper.dataDictionaryRepository.deleteById(id);
    }

}
