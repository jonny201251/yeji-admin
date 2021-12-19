package com.haiying.yeji.controller;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haiying.yeji.common.exception.PageTipException;
import com.haiying.yeji.common.result.ResponseResultWrapper;
import com.haiying.yeji.model.entity.DeptGroup;
import com.haiying.yeji.model.vo.LabelValue;
import com.haiying.yeji.service.DeptGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 部门班组设置 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2021-12-04
 */
@RestController
@RequestMapping("/deptGroup")
@ResponseResultWrapper
public class DeptGroupController{
    @Autowired
    DeptGroupService deptGroupService;

    @GetMapping("list")
    public IPage<DeptGroup> list(int current, int pageSize) {
        LambdaQueryWrapper<DeptGroup> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(DeptGroup::getDeptSort);
        return deptGroupService.page(new Page<>(current, pageSize), wrapper);
    }

    @PostMapping("add")
    public boolean add(@RequestBody DeptGroup group) {
        List<DeptGroup> list = deptGroupService.list(new LambdaQueryWrapper<DeptGroup>().eq(DeptGroup::getName, group.getName()).eq(DeptGroup::getDeptId, group.getDeptId()));
        if (ObjectUtil.isNotEmpty(list)) {
            throw new PageTipException(group.getName() + "已经存在");
        }
        return deptGroupService.save(group);
    }

    @GetMapping("get")
    public DeptGroup getById(String id) {
        return deptGroupService.getById(id);
    }

    @PostMapping("edit")
    public boolean edit(@RequestBody DeptGroup group) {
        return deptGroupService.edit(group);
    }

    @GetMapping("delete")
    public boolean delete(Integer[] arr) {
        List<Integer> idList = Stream.of(arr).collect(Collectors.toList());
        return deptGroupService.removeByIds(idList);
    }

    @GetMapping("getLabelValue")
    public List<LabelValue> getLabelValue(Integer deptId) {
        List<DeptGroup> list = deptGroupService.list(new LambdaQueryWrapper<DeptGroup>().eq(DeptGroup::getDeptId, deptId).orderByAsc(DeptGroup::getDeptSort));
        return list.stream().map(item -> new LabelValue(item.getName(), item.getId())).collect(Collectors.toList());
    }

    @GetMapping("getIdNameMap")
    public Map<Integer, String> getIdNameMap() {
        List<DeptGroup> list = deptGroupService.list();
        return list.stream().collect(Collectors.toMap(DeptGroup::getId, DeptGroup::getName));
    }
}
