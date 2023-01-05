package com.haiying.yeji.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haiying.yeji.common.result.ResponseResultWrapper;
import com.haiying.yeji.model.entity.CheckStatus;
import com.haiying.yeji.service.CheckStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 考核启动和停止设置 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2021-12-04
 */
@RestController
@RequestMapping("/checkStatus")
@ResponseResultWrapper
public class CheckStatusController {
    @Autowired
    CheckStatusService checkStatusService;


    @GetMapping("list")
    public IPage<CheckStatus> list(int current, int pageSize) {
        LambdaQueryWrapper<CheckStatus> wrapper = new LambdaQueryWrapper<CheckStatus>().orderByDesc(CheckStatus::getYear);
        return checkStatusService.page(new Page<>(current, pageSize), wrapper);
    }


    @PostMapping("add")
    public boolean add(@RequestBody CheckStatus checkStatus) {
        return checkStatusService.add(checkStatus);

    }

    @GetMapping("get")
    public CheckStatus get(Integer id) {
        return checkStatusService.getById(id);
    }

    @PostMapping("edit")
    public boolean edit(@RequestBody CheckStatus checkStatus) {
        if (checkStatus.getStatus().equals("启动")) {
            return checkStatusService.edit(checkStatus);
        } else {
            return checkStatusService.updateById(checkStatus);
        }
    }

    @GetMapping("delete")
    public boolean delete(Integer[] arr) {
        List<Integer> idList = Stream.of(arr).collect(Collectors.toList());
        checkStatusService.removeByIds(idList);
        return true;
    }
}
