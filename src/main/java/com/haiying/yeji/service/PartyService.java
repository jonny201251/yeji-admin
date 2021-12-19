package com.haiying.yeji.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.haiying.yeji.model.entity.Party;
import com.haiying.yeji.model.vo.PartyVO;

/**
 * <p>
 * 党支部 服务类
 * </p>
 *
 * @author 作者
 * @since 2021-12-09
 */
public interface PartyService extends IService<Party> {
    boolean edit(PartyVO partyVO);
}
