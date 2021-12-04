package com.haiying.yeji.controller;


import com.haiying.yeji.common.result.ResponseResultWrapper;
import com.haiying.yeji.model.entity.CheckUser;
import com.haiying.yeji.service.CheckUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        System.out.println("aa");
        return super.service.save(checkUser);
    }
}
