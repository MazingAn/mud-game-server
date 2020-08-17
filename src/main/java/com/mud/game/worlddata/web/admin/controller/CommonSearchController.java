package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * 游戏区域Controller
 * 提供ActionAcceptQuest的增删改查
 */
@RestController
@RequestMapping("/commonSearch")
@Api(tags = "后端统一查询接口")
public class CommonSearchController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 统一查询接口
     *
     * @param page
     * @param size
     * @param modelName
     * @return
     */
    @PostMapping("/add")
    @ResponseBody
    public Object query(@RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "20") int size,
                        String modelName,
                        @RequestBody Map<String, String> map) {
        Page<Object> pageResult = null;
        Pageable paging = PageRequest.of(page, size);
        SpecificationRepository repository = DbMapper.modelRepositoryMap.get(modelName);
        if (null == repository) {
            return "查询失败，没有对应的modelName！";
        }
        if (null != map && map.size() > 0){
            //规格定义
            Specification<Object> specification = new Specification<Object>() {

                /**
                 * 构造断言
                 * @param root 实体对象引用
                 * @param query 规则查询对象
                 * @param cb 规则构建对象
                 * @return 断言
                 */
                @Override
                public Predicate toPredicate(Root<Object> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    Predicate p = cb.conjunction();//所有的断言
                    //添加断言
                    Iterator<Map.Entry<String, String>> mapIterator = map.entrySet().iterator();
                    while (mapIterator.hasNext()) {
                        Map.Entry<String, String> entry = mapIterator.next();
                        p = cb.and(p, cb.like(root.get(entry.getKey()).as(String.class), "%" + entry.getValue() + "%"));
                    }
                    return p;
                }
            };
            pageResult = repository.findAll(specification, paging);
        } else{
            pageResult = repository.findAll(paging);
        }
        return pageResult;
    }
}
