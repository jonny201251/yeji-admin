package com.haiying.yeji.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haiying.yeji.common.result.ResponseResultWrapper;
import com.haiying.yeji.model.entity.ChargeDeptLeader;
import com.haiying.yeji.model.vo.ChargeDeptLeaderVO;
import com.haiying.yeji.service.ChargeDeptLeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 主管部门领导 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2021-12-09
 */
@RestController
@RequestMapping("/chargeDeptLeader")
@ResponseResultWrapper
public class ChargeDeptLeaderController {
    @Autowired
    ChargeDeptLeaderService chargeDeptLeaderService;

    @GetMapping("list")
    public IPage<ChargeDeptLeader> list(int current, int pageSize) {
        QueryWrapper<ChargeDeptLeader> wrapper = new QueryWrapper<ChargeDeptLeader>().select("distinct user_name");
        return chargeDeptLeaderService.page(new Page<>(current, pageSize), wrapper);
    }

    @PostMapping("add")
    public boolean add(@RequestBody ChargeDeptLeaderVO chargeDeptLeaderVO) {
        List<ChargeDeptLeader> list = new ArrayList<>();
        for (Integer deptId : chargeDeptLeaderVO.getDeptIdList()) {
            ChargeDeptLeader chargeDeptLeader = new ChargeDeptLeader();
            chargeDeptLeader.setUserName(chargeDeptLeaderVO.getUserName());
            chargeDeptLeader.setDeptId(deptId);
            list.add(chargeDeptLeader);
        }
        return chargeDeptLeaderService.saveBatch(list);
    }

    @GetMapping("get")
    public ChargeDeptLeaderVO get(String userName) {
        List<ChargeDeptLeader> list = chargeDeptLeaderService.list(new LambdaQueryWrapper<ChargeDeptLeader>().eq(ChargeDeptLeader::getUserName, userName));
        ChargeDeptLeaderVO chargeDeptLeaderVO = new ChargeDeptLeaderVO();
        chargeDeptLeaderVO.setUserName(userName);
        chargeDeptLeaderVO.setDeptIdList(list.stream().map(ChargeDeptLeader::getDeptId).collect(Collectors.toList()));
        return chargeDeptLeaderVO;
    }

    @PostMapping("edit")
    public boolean edit(@RequestBody ChargeDeptLeaderVO chargeDeptLeaderVO) {
        //先删除
        chargeDeptLeaderService.remove(new LambdaQueryWrapper<ChargeDeptLeader>().eq(ChargeDeptLeader::getUserName, chargeDeptLeaderVO.getUserName()));
        //后插入
        add(chargeDeptLeaderVO);
        return true;
    }

    @GetMapping("delete")
    public boolean delete(String[] arr) {
        List<String> list = Stream.of(arr).collect(Collectors.toList());
        return chargeDeptLeaderService.remove(new LambdaQueryWrapper<ChargeDeptLeader>().in(ChargeDeptLeader::getUserName, list));
    }
}
