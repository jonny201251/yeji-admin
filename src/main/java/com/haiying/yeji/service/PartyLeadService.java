package com.haiying.yeji.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.haiying.yeji.model.entity.PartyLead;
import com.haiying.yeji.model.vo.PartyLeadVO;

import java.util.List;

/**
 * <p>
 * 主管党支部的党委领导设置 服务类
 * </p>
 *
 * @author 作者
 * @since 2021-12-05
 */
public interface PartyLeadService extends IService<PartyLead> {
    boolean add(PartyLeadVO partyLeadVO);
    boolean edit(PartyLeadVO partyLeadVO);
    boolean delete(List<Integer> idList);
}
