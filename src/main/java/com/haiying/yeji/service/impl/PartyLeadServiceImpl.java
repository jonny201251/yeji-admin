package com.haiying.yeji.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haiying.yeji.common.exception.PageTipException;
import com.haiying.yeji.mapper.PartyLeadMapper;
import com.haiying.yeji.model.entity.PartyLead;
import com.haiying.yeji.model.entity.PartyLead2;
import com.haiying.yeji.model.vo.PartyLeadVO;
import com.haiying.yeji.service.CheckUserService;
import com.haiying.yeji.service.PartyLead2Service;
import com.haiying.yeji.service.PartyLeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * <p>
 * 主管党支部的党委领导设置 服务实现类
 * </p>
 *
 * @author 作者
 * @since 2021-12-05
 */
@Service
public class PartyLeadServiceImpl extends ServiceImpl<PartyLeadMapper, PartyLead> implements PartyLeadService {
    @Autowired
    PartyLead2Service partyLead2Service;
    @Autowired
    CheckUserService checkUserService;

    @Override
    public boolean add(PartyLeadVO partyLeadVO) {
        if (ObjectUtil.isEmpty(partyLeadVO.getPartyNameList())) {
            throw new PageTipException("没有选择党支部");
        }
        //判断是否已存在
        List<PartyLead> dbList = this.list(new LambdaQueryWrapper<PartyLead>().eq(PartyLead::getUserName, partyLeadVO.getUserName()));
        if (ObjectUtil.isNotEmpty(dbList)) {
            throw new PageTipException("姓名已存在");
        }
        //
        PartyLead partyLead = new PartyLead();
        partyLead.setUserName(partyLeadVO.getUserName());
        this.save(partyLead);

        List<PartyLead2> partyLead2List = new ArrayList<>();
        Set<String> set = new TreeSet<>(partyLeadVO.getPartyNameList());
        set.forEach(partyName -> {
            PartyLead2 partyLead2 = new PartyLead2();
            partyLead2.setPartyLeadId(partyLead.getId());
            partyLead2.setPartyName(partyName);
            partyLead2List.add(partyLead2);
        });
        partyLead2Service.saveBatch(partyLead2List);

        return true;
    }

    @Override
    public boolean edit(PartyLeadVO partyLeadVO) {
        PartyLead partyLead = this.getOne(new LambdaQueryWrapper<PartyLead>().eq(PartyLead::getUserName, partyLeadVO.getUserName()));
        //先删除
        this.remove(new LambdaQueryWrapper<PartyLead>().eq(PartyLead::getUserName, partyLeadVO.getUserName()));
        partyLead2Service.remove(new LambdaQueryWrapper<PartyLead2>().eq(PartyLead2::getPartyLeadId, partyLead.getId()));
        //后插入
        return add(partyLeadVO);
    }

    @Override
    public boolean delete(List<Integer> idList) {
        this.removeByIds(idList);
        partyLead2Service.remove(new LambdaQueryWrapper<PartyLead2>().in(PartyLead2::getPartyLeadId, idList));
        return true;
    }
}
