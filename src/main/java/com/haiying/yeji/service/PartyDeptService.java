package com.haiying.yeji.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.haiying.yeji.model.entity.PartyDept;
import com.haiying.yeji.model.vo.PartyDeptVO;

import java.util.List;

/**
 * <p>
 * 党支部与部门的设置 服务类
 * </p>
 *
 * @author 作者
 * @since 2021-12-05
 */
public interface PartyDeptService extends IService<PartyDept> {
    boolean add(PartyDeptVO partyDeptVO);
    boolean edit(PartyDeptVO partyDeptVO);
    boolean delete(List<Integer> idList);
}
