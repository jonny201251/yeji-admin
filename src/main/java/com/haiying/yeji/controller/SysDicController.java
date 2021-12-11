package com.haiying.yeji.controller;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haiying.yeji.common.result.ResponseResultWrapper;
import com.haiying.yeji.model.entity.SysDic;
import com.haiying.yeji.model.vo.LabelValue;
import com.haiying.yeji.service.SysDicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 数据字典 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2021-11-17
 */
@RestController
@RequestMapping("/sysDic")
@ResponseResultWrapper
public class SysDicController {
    @Autowired
    SysDicService sysDicService;

    @GetMapping("list")
    public IPage<SysDic> list(int current, int pageSize, String flag, String name) {
        LambdaQueryWrapper<SysDic> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotEmpty(flag)) {
            wrapper.like(SysDic::getFlag, flag);
        }
        if (ObjectUtil.isNotEmpty(name)) {
            wrapper.like(SysDic::getName, name);
        }
        wrapper.orderByAsc(SysDic::getSort);
        return sysDicService.page(new Page<>(current, pageSize), wrapper);
    }

    @PostMapping("add")
    public boolean add(@RequestBody SysDic sysDic) {
        return sysDicService.save(sysDic);
    }

    @GetMapping("get")
    public SysDic getById(String id) {
        return sysDicService.getById(id);
    }

    @PostMapping("edit")
    public boolean edit(@RequestBody SysDic sysDic) {
        return sysDicService.updateById(sysDic);
    }

    @GetMapping("delete")
    public boolean delete(Integer[] idArr) {
        List<Integer> idList = Stream.of(idArr).collect(Collectors.toList());
        return sysDicService.removeByIds(idList);
    }

    @GetMapping("getLabelValue")
    public List<LabelValue> getLabelValue(String flag) {
        List<SysDic> list = sysDicService.list(new LambdaQueryWrapper<SysDic>().eq(SysDic::getFlag, flag).orderByAsc(SysDic::getSort));
        return list.stream().map(item -> new LabelValue(item.getName(), item.getName())).collect(Collectors.toList());
    }

}
