package com.haiying.yeji.controller;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haiying.yeji.common.exception.PageTipException;
import com.haiying.yeji.common.result.ResponseResultWrapper;
import com.haiying.yeji.common.utils.TreeUtil;
import com.haiying.yeji.model.entity.*;
import com.haiying.yeji.model.vo.LabelValue;
import com.haiying.yeji.model.vo.UserVO;
import com.haiying.yeji.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
    @Autowired
    PartyService partyService;
    @Autowired
    SysPermissionService sysPermissionService;

    @GetMapping("haveLogin")
    public boolean haveLogin() {
        CheckUser user = (CheckUser) httpSession.getAttribute("user");
        if (user == null) {
            throw new PageTipException("用户未登录");
        }
        return true;
    }

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
        checkUser.setPassword(SecureUtil.md5("1"));
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
        if (user == null) {
            throw new PageTipException("用户未登录");
        }
        user.setPassword(SecureUtil.md5(password1));
        return checkUserService.updateById(user);
    }

    //管理人员初始化密码
    @GetMapping("initPassword")
    public boolean adminChangePassword(Integer id) {
        CheckUser user = checkUserService.getById(id);
        user.setPassword(SecureUtil.md5("1"));
        return checkUserService.updateById(user);
    }

    @PostMapping("login")
    public UserVO login(@RequestBody CheckUser user) {
        //根据 登录账号 查询出用户
        List<CheckUser> userList = checkUserService.list(new LambdaQueryWrapper<CheckUser>().eq(CheckUser::getName, user.getName()));
        if (userList.size() != 1) {
            throw new PageTipException("用户名错误");
        }
        CheckUser dbUser = userList.get(0);
        //校验 登录密码
        String dbPassword = dbUser.getPassword();
        String pagePassword = SecureUtil.md5(user.getPassword());
        if (!dbPassword.equals(pagePassword)) {
            throw new PageTipException("密码错误");
        }
        //
        UserVO userVO = new UserVO();
        userVO.setUser(dbUser);

        List<SysPermission> menuList;
        if (dbUser.getName().equals("张强")) {
            menuList = sysPermissionService.list();
        } else if (dbUser.getName().equals("陈玉莲")) {
            menuList = sysPermissionService.list(new LambdaQueryWrapper<SysPermission>().in(SysPermission::getId, Arrays.asList(6, 14, 15, 19, 20, 21)));
        } else if (dbUser.getUserType().equals("公司领导")) {
            menuList = sysPermissionService.list(new LambdaQueryWrapper<SysPermission>().in(SysPermission::getId, Arrays.asList(14, 15, 16, 18)));
        } else if (dbUser.getUserType().equals("中层领导")) {
            menuList = sysPermissionService.list(new LambdaQueryWrapper<SysPermission>().in(SysPermission::getId, Arrays.asList(14, 15, 16, 17)));
        } else {
            menuList = sysPermissionService.list(new LambdaQueryWrapper<SysPermission>().in(SysPermission::getId, Arrays.asList(14, 15)));
        }
        userVO.setMenuList(TreeUtil.getTree(menuList));
        //
        httpSession.removeAttribute("user");
        httpSession.setAttribute("user", dbUser);
        return userVO;
    }

    @GetMapping("logout")
    public boolean logout() {
        httpSession.removeAttribute("user");
        return true;
    }

    //更新人员数据
    @GetMapping("updatee")
    public boolean updatee() {
        List<CheckUser> dataList = new ArrayList<>();
        Map<Integer, String> map = partyService.list().stream().collect(Collectors.toMap(Party::getDeptId, Party::getPartyName));
        List<CheckUser> list = checkUserService.list();
        for (CheckUser checkUser : list) {
            //更新党支部
            if (checkUser.getHavePartyMember().equals("是")) {
                checkUser.setPartyName(map.get(checkUser.getDeptId()));
                dataList.add(checkUser);
            }
            //更新人员类型

        }
        checkUserService.updateBatchById(dataList);
        return true;
    }
}
