package com.haiying.yeji.model.vo;

import com.haiying.yeji.model.entity.CheckUser;
import com.haiying.yeji.model.entity.SysPermission;
import lombok.Data;

import java.util.List;

@Data
public class UserVO {
    //用户
    private CheckUser user;
    //导航菜单
    private List<SysPermission> menuList;
}
