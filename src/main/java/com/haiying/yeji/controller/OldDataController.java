package com.haiying.yeji.controller;

import cn.hutool.core.util.ObjectUtil;
import com.haiying.yeji.model.entity.CheckUser;
import com.haiying.yeji.model.entity.DeptGroup;
import com.haiying.yeji.model.entity.SysDept;
import com.haiying.yeji.service.CheckUserService;
import com.haiying.yeji.service.DeptGroupService;
import com.haiying.yeji.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class OldDataController {
    @Autowired
    CheckUserService service;
    @Autowired
    SysDeptService sysDeptService;

    @Autowired
    DeptGroupService deptGroupService;

    @GetMapping("a")
    public boolean add() {
        Map<String, SysDept> deptMap = sysDeptService.list().stream().collect(Collectors.toMap(SysDept::getName, a -> a));
        List<DeptGroup> groupList = deptGroupService.list();

        List<CheckUser> list = service.list();
        for (CheckUser checkUser : list) {
            if(ObjectUtil.isNotEmpty(checkUser.getGroupName())) {
                List<DeptGroup> collect = groupList.stream().filter(item -> item.getDeptId().equals(checkUser.getDeptId()) && item.getName().equals(checkUser.getGroupName())).collect(Collectors.toList());
                if (collect.size() != 1) {
                   throw new RuntimeException(checkUser.getName()+","+checkUser.getDeptName()+","+checkUser.getGroupName());
                } else {
                    checkUser.setGroupId(collect.get(0).getId());
                }
            }

        }
        service.updateBatchById(list);
        return true;
    }


}
