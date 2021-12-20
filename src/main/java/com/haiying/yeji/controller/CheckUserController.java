package com.haiying.yeji.controller;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haiying.yeji.common.result.ResponseResultWrapper;
import com.haiying.yeji.model.entity.CheckUser;
import com.haiying.yeji.model.entity.DeptGroup;
import com.haiying.yeji.model.entity.SysDept;
import com.haiying.yeji.model.vo.LabelValue;
import com.haiying.yeji.service.CheckUserService;
import com.haiying.yeji.service.DeptGroupService;
import com.haiying.yeji.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
    @Autowired
    DeptGroupService deptGroupService;
    @Autowired
    HttpSession httpSession;

    @GetMapping("list")
    public IPage<CheckUser> list(int current, int pageSize,
                                 String name, String gender, Integer deptId, String userRole, String userType,
                                 String havePartyMember, String partyName, String partyRole, String groupName, String workStatus) {
        LambdaQueryWrapper<CheckUser> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotEmpty(name)) {
            wrapper.like(CheckUser::getName, name);
        }
        if (ObjectUtil.isNotEmpty(gender)) {
            wrapper.eq(CheckUser::getGender, name);
        }
        if (ObjectUtil.isNotEmpty(deptId)) {
            wrapper.eq(CheckUser::getDeptId, deptId);
        }
        if (ObjectUtil.isNotEmpty(userRole)) {
            wrapper.eq(CheckUser::getUserRole, userRole);
        }
        if (ObjectUtil.isNotEmpty(userType)) {
            wrapper.eq(CheckUser::getUserType, userType);
        }
        if (ObjectUtil.isNotEmpty(havePartyMember)) {
            wrapper.eq(CheckUser::getHavePartyMember, havePartyMember);
        }
        if (ObjectUtil.isNotEmpty(partyName)) {
            wrapper.eq(CheckUser::getPartyName, partyName);
        }
        if (ObjectUtil.isNotEmpty(partyRole)) {
            wrapper.eq(CheckUser::getPartyRole, partyRole);
        }
        if (ObjectUtil.isNotEmpty(groupName)) {
            wrapper.like(CheckUser::getGroupName, groupName);
        }
        if (ObjectUtil.isNotEmpty(workStatus)) {
            wrapper.eq(CheckUser::getWorkStatus, workStatus);
        }

        return checkUserService.page(new Page<>(current, pageSize), wrapper);
    }

    private void pre(CheckUser checkUser) {
        SysDept dept = sysDeptService.getById(checkUser.getDeptId());
        checkUser.setDeptName(dept.getName());
        checkUser.setDeptSort(dept.getSort());
        if (checkUser.getHavePartyMember().equals("否")) {
            checkUser.setPartyName(null);
            checkUser.setPartyRole(null);
        }
        if (ObjectUtil.isEmpty(checkUser.getGroupId())) {
            checkUser.setGroupId(null);
            checkUser.setGroupName(null);
        } else {
            DeptGroup group = deptGroupService.getById(checkUser.getGroupId());
            checkUser.setGroupName(group.getName());
        }
    }

    @PostMapping("add")
    public boolean add(@RequestBody CheckUser checkUser) {
        pre(checkUser);
        checkUser.setPassword(SecureUtil.md5("123456"));
        return checkUserService.save(checkUser);
    }


    @GetMapping("get")
    public CheckUser get(String id) {
        return checkUserService.getById(id);
    }

    @PostMapping("edit")
    public boolean edit(@RequestBody CheckUser checkUser) {
        pre(checkUser);
        return checkUserService.updateById(checkUser);
    }

    @GetMapping("delete")
    public boolean delete(Integer[] arr) {
        List<Integer> idList = Stream.of(arr).collect(Collectors.toList());
        return checkUserService.removeByIds(idList);
    }

    @GetMapping("getChargeDeptLeader")
    public List<LabelValue> getLeadName() {
        List<Integer> deptIdList = sysDeptService.list(new LambdaQueryWrapper<SysDept>().in(SysDept::getName, Arrays.asList("副总师级", "财务副总监"))).stream().map(SysDept::getId).collect(Collectors.toList());
        List<CheckUser> list = checkUserService.list(new LambdaQueryWrapper<CheckUser>().eq(CheckUser::getUserType, "公司领导").or().in(CheckUser::getDeptId, deptIdList));
        return list.stream().map(item -> new LabelValue(item.getName(), item.getName())).collect(Collectors.toList());
    }

    //用户自己，修改密码
    @GetMapping("changePassword")
    public boolean changePassword(String password1) {
        CheckUser user = (CheckUser) httpSession.getAttribute("user");
        user.setPassword(SecureUtil.md5(password1));
        return checkUserService.updateById(user);
    }

    //管理人员初始化密码
    @GetMapping("initPassword")
    public boolean adminChangePassword(Integer id) {
        CheckUser user = checkUserService.getById(id);
        user.setPassword(SecureUtil.md5("123456"));
        return checkUserService.updateById(user);
    }
}
