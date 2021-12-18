package com.haiying.yeji.controller;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haiying.yeji.common.result.ResponseResultWrapper;
import com.haiying.yeji.model.entity.CheckUser;
import com.haiying.yeji.model.entity.SysDept;
import com.haiying.yeji.model.vo.LabelValue;
import com.haiying.yeji.service.CheckUserService;
import com.haiying.yeji.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 考核人员信息设置 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2021-12-04
 */
@RestController
@RequestMapping("/checkUser")
@ResponseResultWrapper
public class CheckUserController {
    @Autowired
    CheckUserService checkUserService;
    @Autowired
    SysDeptService sysDeptService;

    @GetMapping("list")
    public IPage<CheckUser> list(int current, int pageSize, String name) {
        LambdaQueryWrapper<CheckUser> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotEmpty(name)) {
            wrapper.like(CheckUser::getName, name);
        }
        return checkUserService.page(new Page<>(current, pageSize), wrapper);
    }

    @PostMapping("add")
    public boolean add(@RequestBody CheckUser checkUser) {
        return checkUserService.save(checkUser);
    }


    @GetMapping("get")
    public CheckUser get(String id) {
        return checkUserService.getById(id);
    }

    @PostMapping("edit")
    public boolean edit(@RequestBody CheckUser checkUser) {
        return checkUserService.updateById(checkUser);
    }

    @GetMapping("delete")
    public boolean delete(Integer[] arr) {
        List<Integer> idList = Stream.of(arr).collect(Collectors.toList());
        return checkUserService.removeByIds(idList);
    }

    @GetMapping("getChargeDeptLeader")
    public List<LabelValue> getLeadName() {
        List<SysDept> deptIdList = sysDeptService.list(new LambdaQueryWrapper<SysDept>().in(SysDept::getName, Arrays.asList("副总师级", "财务副总监")));
        List<CheckUser> list = checkUserService.list(new LambdaQueryWrapper<CheckUser>().eq(CheckUser::getUserType, "公司领导").or().in(CheckUser::getDeptId,deptIdList));
        return list.stream().map(item -> new LabelValue(item.getName(), item.getName())).collect(Collectors.toList());
    }
}
