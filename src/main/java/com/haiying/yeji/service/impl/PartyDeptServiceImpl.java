package com.haiying.yeji.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haiying.yeji.common.exception.PageTipException;
import com.haiying.yeji.mapper.PartyDeptMapper;
import com.haiying.yeji.model.entity.PartyDept;
import com.haiying.yeji.model.entity.PartyDept2;
import com.haiying.yeji.model.vo.PartyDeptVO;
import com.haiying.yeji.service.PartyDept2Service;
import com.haiying.yeji.service.PartyDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * <p>
 * 党支部与部门的设置 服务实现类
 * </p>
 *
 * @author 作者
 * @since 2021-12-05
 */
@Service
public class PartyDeptServiceImpl extends ServiceImpl<PartyDeptMapper, PartyDept> implements PartyDeptService {
    @Autowired
    PartyDept2Service partyDept2Service;

    @Override
    public boolean add(PartyDeptVO partyDeptVO) {
        if (ObjectUtil.isEmpty(partyDeptVO.getDeptIdList())) {
            throw new PageTipException("没有选择部门");
        }
        //判断是否已存在
        List<PartyDept> dbList = this.list(new LambdaQueryWrapper<PartyDept>().eq(PartyDept::getPartyName, partyDeptVO.getPartyName()));
        if (ObjectUtil.isNotEmpty(dbList)) {
            throw new PageTipException("党支部名称已存在");
        }
        //
        PartyDept partyDept = new PartyDept();
        partyDept.setPartyName(partyDeptVO.getPartyName());
        this.save(partyDept);

        List<PartyDept2> partyDept2List = new ArrayList<>();
        Set<Integer> set = new TreeSet<>(partyDeptVO.getDeptIdList());
        set.forEach(deptId -> {
            PartyDept2 partyDept2 = new PartyDept2();
            partyDept2.setPartyDeptId(partyDept.getId());
            partyDept2.setDeptId(deptId);
            partyDept2List.add(partyDept2);
        });
        partyDept2Service.saveBatch(partyDept2List);

        return true;
    }

    @Override
    public boolean edit(PartyDeptVO partyDeptVO) {
        PartyDept partyDept = this.getOne(new LambdaQueryWrapper<PartyDept>().eq(PartyDept::getPartyName, partyDeptVO.getPartyName()));
        //先删除
        this.remove(new LambdaQueryWrapper<PartyDept>().eq(PartyDept::getPartyName, partyDeptVO.getPartyName()));
        partyDept2Service.remove(new LambdaQueryWrapper<PartyDept2>().eq(PartyDept2::getPartyDeptId, partyDept.getId()));
        //后插入
        return add(partyDeptVO);
    }

    @Override
    public boolean delete(List<Integer> idList) {
        this.removeByIds(idList);
        partyDept2Service.remove(new LambdaQueryWrapper<PartyDept2>().in(PartyDept2::getPartyDeptId, idList));
        return true;
    }
}
