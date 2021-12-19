package com.haiying.yeji.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haiying.yeji.mapper.DeptGroupMapper;
import com.haiying.yeji.model.entity.CheckUser;
import com.haiying.yeji.model.entity.DeptGroup;
import com.haiying.yeji.service.CheckUserService;
import com.haiying.yeji.service.DeptGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 班组 服务实现类
 * </p>
 *
 * @author 作者
 * @since 2021-12-09
 */
@Service
public class DeptGroupServiceImpl extends ServiceImpl<DeptGroupMapper, DeptGroup> implements DeptGroupService {
    @Autowired
    CheckUserService checkUserService;

    @Override
    public boolean edit(DeptGroup group) {
        this.updateById(group);
        //checkuser
        List<CheckUser> list = checkUserService.list(new LambdaQueryWrapper<CheckUser>().eq(CheckUser::getDeptId, group.getDeptId()).eq(CheckUser::getGroupId, group.getId()));
        if (ObjectUtil.isNotEmpty(list)) {
            for (CheckUser checkUser : list) {
                checkUser.setGroupName(group.getName());
            }
            checkUserService.updateBatchById(list);
        }
        return true;
    }
}
