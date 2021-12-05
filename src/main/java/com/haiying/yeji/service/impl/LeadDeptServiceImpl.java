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
            throw new PageTipException("没有选择部门");
        }
        //判断是否已存在
        List<LeadDept> dbList = this.list(new LambdaQueryWrapper<LeadDept>().eq(LeadDept::getUserName, leadDeptVO.getUserName()));
        if (ObjectUtil.isNotEmpty(dbList)) {
            throw new PageTipException("姓名已存在");
        }
        //
        CheckUser user = checkUserService.getOne(new LambdaQueryWrapper<CheckUser>().eq(CheckUser::getName, leadDeptVO.getUserName()));

        LeadDept leadDept = new LeadDept();
        leadDept.setUserName(leadDeptVO.getUserName());
        leadDept.setSort(user.getSort());
        this.save(leadDept);

        List<LeadDept2> leadDept2List = new ArrayList<>();
        Set<Integer> set = new TreeSet<>(leadDeptVO.getDeptIdList());
        set.forEach(deptId -> {
            LeadDept2 leadDept2 = new LeadDept2();
            leadDept2.setLeadDeptId(leadDept.getId());
            leadDept2.setDeptId(deptId);
            leadDept2List.add(leadDept2);
        });
        leadDept2Service.saveBatch(leadDept2List);

        return true;
    }

    @Override
    public boolean edit(LeadDeptVO leadDeptVO) {
        LeadDept leadDept = this.getOne(new LambdaQueryWrapper<LeadDept>().eq(LeadDept::getUserName, leadDeptVO.getUserName()));
        //先删除
        this.remove(new LambdaQueryWrapper<LeadDept>().eq(LeadDept::getUserName, leadDeptVO.getUserName()));
        leadDept2Service.remove(new LambdaQueryWrapper<LeadDept2>().eq(LeadDept2::getLeadDeptId, leadDept.getId()));
        //后插入
        return add(leadDeptVO);
    }

    @Override
    public boolean delete(List<Integer> idList) {
        this.removeByIds(idList);
        leadDept2Service.remove(new LambdaQueryWrapper<LeadDept2>().in(LeadDept2::getLeadDeptId, idList));
        return true;
    }
}
