package com.haiying.yeji.controller;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.haiying.yeji.common.exception.PageTipException;
import com.haiying.yeji.common.result.ResponseResultWrapper;
import com.haiying.yeji.common.utils.TreeUtil;
import com.haiying.yeji.model.entity.SysDept;
import com.haiying.yeji.model.vo.LabelValue;
import com.haiying.yeji.model.vo.TreeSelect;
import com.haiying.yeji.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 部门表 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2021-12-01
 */
@RestController
@RequestMapping("/sysDept")
@ResponseResultWrapper
public class SysDeptController {
    @Autowired
    private SysDeptService sysDeptService;

    @GetMapping("list")
    public List<SysDept> list() {
        List<SysDept> list = sysDeptService.list(new LambdaQueryWrapper<SysDept>().orderByAsc(SysDept::getSort));
        return TreeUtil.getTree(list);
    }

    @PostMapping("add")
    public boolean add(@RequestBody SysDept sysDept) {
        List<SysDept> list = sysDeptService.list(new LambdaQueryWrapper<SysDept>().eq(SysDept::getName, sysDept.getName()));
        if (ObjectUtil.isNotEmpty(list)) {
            throw new PageTipException(sysDept.getName() + "--已存在");
        }
        return sysDeptService.save(sysDept);
    }

    @GetMapping("get")
    public SysDept getById(String id) {
        return sysDeptService.getById(id);
    }

    @PostMapping("edit")
    public boolean edit(@RequestBody SysDept sysDept) {
        return sysDeptService.edit(sysDept);
    }

    @GetMapping("delete")
    public boolean delete(Integer[] arr) {
        List<Integer> idList = Stream.of(arr).collect(Collectors.toList());
        //根据idList，取出所有的子节点
        List<Integer> list = new ArrayList<>(idList);
        while (true) {
            List<SysDept> tmp = sysDeptService.list(new LambdaQueryWrapper<SysDept>().in(SysDept::getPid, idList));
            if (ObjectUtil.isEmpty(tmp)) {
                break;
            } else {
                idList = tmp.stream().map(SysDept::getId).collect(Collectors.toList());
                list.addAll(idList);
            }
        }
        return sysDeptService.remove(new LambdaQueryWrapper<SysDept>().in(SysDept::getId, list));
    }

    @GetMapping("getTreeSelect")
    public List<TreeSelect> getTreeSelect() {
        List<SysDept> list = sysDeptService.list(new LambdaQueryWrapper<SysDept>().orderByAsc(SysDept::getSort));
        return TreeUtil.getTreeSelect(list);
    }

    @GetMapping("getTreeSelect2")
    public List<TreeSelect> getTreeSelect2() {
        List<SysDept> list = sysDeptService.list(new LambdaQueryWrapper<SysDept>().notIn(SysDept::getName, Arrays.asList("公司领导", "安全生产总监", "副总师级", "财务副总监","离退休办公室")).orderByAsc(SysDept::getSort));
        return TreeUtil.getTreeSelect(list);
    }

    @GetMapping("getLabelValue")
    public List<LabelValue> getLabelValue() {
        List<SysDept> list = sysDeptService.list(new LambdaQueryWrapper<SysDept>().orderByAsc(SysDept::getSort));
        return list.stream().map(item -> new LabelValue(item.getName(), item.getId())).collect(Collectors.toList());
    }

    @GetMapping("getIdNameMap")
    public Map<Integer, String> getIdNameMap() {
        List<SysDept> list = sysDeptService.list();
        return list.stream().collect(Collectors.toMap(SysDept::getId, SysDept::getName));
    }
}
