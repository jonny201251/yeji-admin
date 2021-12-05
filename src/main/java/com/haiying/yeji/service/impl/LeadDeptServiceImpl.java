package com.haiying.yeji.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haiying.yeji.common.exception.PageTipException;
import com.haiying.yeji.mapper.LeadDeptMapper;
import com.haiying.yeji.model.entity.CheckUser;
import com.haiying.yeji.model.entity.LeadDept;
import com.haiying.yeji.model.entity.LeadDept2;
import com.haiying.yeji.model.vo.LeadDeptVO;
import com.haiying.yeji.service.CheckUserService;
import com.haiying.yeji.service.LeadDept2Service;
import com.haiying.yeji.service.LeadDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * <p>
 * 公司领导主管部门设置 服务实现类
 * </p>
 *
 * @author 作者
 * @since 2021-12-04
 */
@Service
public class LeadDeptServiceImpl extends ServiceImpl<LeadDeptMapper, LeadDept> implements LeadDeptService {
    @Autowired
    LeadDept2Service leadDept2Service;
    @Autowired
    CheckUserService checkUserService;

    @Override
    public boolean add(LeadDeptVO leadDeptVO) {
        if (ObjectUtil.isEmpty(leadDeptVO.getDeptIdList())) {
            throw new PageTipException("需要选择主管的部门");
        }
        CheckUser user = checkUserService.getOne(new LambdaQueryWrapper<CheckUser>().eq(CheckUser::getName, leadDeptVO.getUserName()));

        LeadDept leadDept = new LeadDept();
        leadDept.setUserName(leadDeptVO.getUserName());
        leadDept.setSort(user.getSort());
        this.save(leadDept);

        List<LeadDept2> leadDept2List = new ArrayList<>();
        Set<Integer> set=new TreeSet<>(leadDeptVO.getDeptIdList());
        set.forEach(deptId -> {
            LeadDept2 leadDept2 = new LeadDept2();
            leadDept2.setUserName(leadDeptVO.getUserName());
            leadDept2.setDeptId(deptId);
            leadDept2List.add(leadDept2);
        });
        leadDept2Service.saveBatch(leadDept2List);

        return true;
    }

    @Override
    public boolean edit(LeadDeptVO leadDeptVO) {
        return true;
    }

    @Override
    public boolean delete(List<String> userNameList) {
        this.remove(new LambdaQueryWrapper<LeadDept>().in(LeadDept::getUserName, userNameList));
        return true;
    }
}
