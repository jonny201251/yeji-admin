package com.haiying.yeji.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haiying.yeji.common.result.ResponseResultWrapper;
import com.haiying.yeji.model.entity.LeadDept;
import com.haiying.yeji.model.vo.LeadDeptVO;
import com.haiying.yeji.service.LeadDept2Service;
import com.haiying.yeji.service.LeadDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 公司领导主管部门设置 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2021-12-04
 */
@RestController
@RequestMapping("/leadDept")
@ResponseResultWrapper
public class LeadDeptController {
    @Autowired
    LeadDeptService leadDeptService;
    @Autowired
    LeadDept2Service leadDept2Service;

    @GetMapping("list")
    public IPage<LeadDept> list(int current, int pageSize) {
        LambdaQueryWrapper<LeadDept> wrapper = new LambdaQueryWrapper<>();
        return leadDeptService.page(new Page<>(current, pageSize), wrapper);
    }

    @PostMapping("add")
    public boolean add(@RequestBody LeadDeptVO leadDeptVO) {
        return leadDeptService.add(leadDeptVO);
    }

    @GetMapping("get")
    public LeadDeptVO getById(String id) {
        return null;
    }

    @PostMapping("edit")
    public boolean edit(@RequestBody LeadDeptVO leadDeptVO) {
        return leadDeptService.edit(leadDeptVO);
    }

    @GetMapping("delete")
    public boolean delete(String[] arr) {
        List<String> userNameList = Stream.of(arr).collect(Collectors.toList());
        return leadDeptService.delete(userNameList);
    }

}
