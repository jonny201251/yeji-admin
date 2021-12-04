package com.haiying.yeji.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haiying.yeji.mapper.LeadDeptMapper;
import com.haiying.yeji.model.entity.LeadDept;
import com.haiying.yeji.model.vo.LeadDeptVO;
import com.haiying.yeji.service.LeadDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    LeadDeptService leadDeptService;

    @Override
    public boolean add(LeadDeptVO leadDeptVO) {

        return true;
    }

    @Override
    public boolean edit(LeadDeptVO leadDeptVO) {
        return true;
    }

    @Override
    public boolean delete(List<String> userNameList) {
        this.remove(new LambdaQueryWrapper<LeadDept>().in(LeadDept::getUserName,userNameList));
        return true;
    }
}
