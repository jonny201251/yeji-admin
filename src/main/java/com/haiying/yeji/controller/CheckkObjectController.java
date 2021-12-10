package com.haiying.yeji.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haiying.yeji.common.result.ResponseResultWrapper;
import com.haiying.yeji.model.entity.CheckkObject;
import com.haiying.yeji.model.vo.CheckkObjectVO;
import com.haiying.yeji.service.CheckkObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 被考核对象 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2021-12-09
 */
@RestController
@RequestMapping("/checkkObject")
@ResponseResultWrapper
public class CheckkObjectController {
    @Autowired
    CheckkObjectService checkkObjectService;

    @GetMapping("list")
    public IPage<CheckkObject> list(int current, int pageSize) {
        QueryWrapper<CheckkObject> wrapper = new QueryWrapper<CheckkObject>().select("distinct checkk_object");
        return checkkObjectService.page(new Page<>(current, pageSize), wrapper);
    }

    @PostMapping("add")
    public boolean add(@RequestBody CheckkObjectVO checkkObjectVO) {
        for (CheckkObject checkkObject : checkkObjectVO.getCheckList()) {
            //edit
            checkkObject.setId(null);

            checkkObject.setCheckkObject(checkkObjectVO.getCheckkObject());
        }
        return checkkObjectService.saveBatch(checkkObjectVO.getCheckList());
    }

    @GetMapping("get")
    public CheckkObjectVO get(String checkkObject) {
        CheckkObjectVO checkkObjectVO = new CheckkObjectVO();
        checkkObjectVO.setCheckkObject(checkkObject);
        List<CheckkObject> list = checkkObjectService.list(new LambdaQueryWrapper<CheckkObject>().eq(CheckkObject::getCheckkObject, checkkObject));
        checkkObjectVO.setCheckList(list);
        return checkkObjectVO;
    }

    @PostMapping("edit")
    public boolean edit(@RequestBody CheckkObjectVO checkkObjectVO) {
        //先删除
        checkkObjectService.remove(new LambdaQueryWrapper<CheckkObject>().eq(CheckkObject::getCheckkObject, checkkObjectVO.getCheckkObject()));
        //后插入
        add(checkkObjectVO);
        return true;
    }

    @GetMapping("delete")
    public boolean delete(String[] arr) {
        List<String> list = Stream.of(arr).collect(Collectors.toList());
        return checkkObjectService.remove(new LambdaQueryWrapper<CheckkObject>().in(CheckkObject::getCheckkObject, list));
    }
}
