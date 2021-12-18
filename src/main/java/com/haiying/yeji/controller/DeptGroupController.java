package com.haiying.yeji.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haiying.yeji.common.result.ResponseResultWrapper;
import com.haiying.yeji.model.entity.DeptGroup;
import com.haiying.yeji.model.vo.LabelValue;
import com.haiying.yeji.service.DeptGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
public class DeptGroupController extends BaseController<DeptGroup> {
    @Autowired
    DeptGroupService deptGroupService;

    @Override
    @GetMapping("list")
    public IPage<DeptGroup> list(int current, int pageSize) {
        LambdaQueryWrapper<DeptGroup> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(DeptGroup::getDeptSort);
        return deptGroupService.page(new Page<>(current, pageSize), wrapper);
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
