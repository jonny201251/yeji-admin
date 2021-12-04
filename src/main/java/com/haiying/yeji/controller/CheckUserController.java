package com.haiying.yeji.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.haiying.yeji.common.result.ResponseResultWrapper;
import com.haiying.yeji.model.entity.CheckUser;
import com.haiying.yeji.model.vo.LabelValue;
import com.haiying.yeji.service.CheckUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
public class CheckUserController extends BaseController<CheckUser> {
    @Autowired
    CheckUserService checkUserService;

    @Override
    @PostMapping("add")
    public boolean add(@RequestBody CheckUser checkUser) {
        return checkUserService.save(checkUser);
    }

    @GetMapping("getLeadName")
    public List<LabelValue> getLeadName() {
        List<CheckUser> list = checkUserService.list(new LambdaQueryWrapper<CheckUser>().eq(CheckUser::getUserType, "公司领导"));
        return list.stream().map(item -> new LabelValue(item.getName(), item.getName())).collect(Collectors.toList());
    }
}
