package com.haiying.yeji.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haiying.yeji.mapper.PartyMapper;
import com.haiying.yeji.model.entity.CheckUser;
import com.haiying.yeji.model.entity.Party;
import com.haiying.yeji.model.vo.PartyVO;
import com.haiying.yeji.service.CheckUserService;
import com.haiying.yeji.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 党支部 服务实现类
 * </p>
 *
 * @author 作者
 * @since 2021-12-09
 */
@Service
public class PartyServiceImpl extends ServiceImpl<PartyMapper, Party> implements PartyService {
    @Autowired
    CheckUserService checkUserService;

    @Override
    public boolean edit(PartyVO partyVO) {
        //先删除
        this.remove(new LambdaQueryWrapper<Party>().eq(Party::getPartyName, partyVO.getOldPartyName()));
        //后插入
        List<Party> list = new ArrayList<>();
        for (Integer deptId : partyVO.getDeptIdList()) {
            Party party = new Party();
            party.setPartyName(partyVO.getPartyName());
            party.setDeptId(deptId);
            party.setSort(partyVO.getSort());
            list.add(party);
        }
        this.saveBatch(list);
        //checkuser
        List<CheckUser> list2 = checkUserService.list(new LambdaQueryWrapper<CheckUser>().eq(CheckUser::getPartyName, partyVO.getOldPartyName()));
        if (ObjectUtil.isNotEmpty(list2)) {
            for (CheckUser checkUser : list2) {
                checkUser.setPartyName(partyVO.getPartyName());
            }
            checkUserService.updateBatchById(list2);
        }
        return true;
    }
}
