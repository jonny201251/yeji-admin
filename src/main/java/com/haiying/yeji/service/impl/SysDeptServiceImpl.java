package com.haiying.yeji.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haiying.yeji.mapper.SysDeptMapper;
import com.haiying.yeji.model.entity.CheckUser;
import com.haiying.yeji.model.entity.SysDept;
import com.haiying.yeji.service.CheckUserService;
import com.haiying.yeji.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author 作者
 * @since 2021-12-01
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {
    @Autowired
    CheckUserService checkUserService;

    @Override
    public boolean edit(SysDept sysDept) {
        this.updateById(sysDept);
        //checkuser
        List<CheckUser> list = checkUserService.list(new LambdaQueryWrapper<CheckUser>().eq(CheckUser::getDeptId, sysDept.getId()));
        if (ObjectUtil.isNotEmpty(list)) {
            for (CheckUser checkUser : list) {
                checkUser.setDeptName(sysDept.getName());
                checkUser.setDeptSort(sysDept.getSort());
            }
            checkUserService.updateBatchById(list);
        }
        return true;
    }
}
