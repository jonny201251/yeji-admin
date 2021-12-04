package com.haiying.yeji.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.haiying.yeji.model.entity.LeadDept;
import com.haiying.yeji.model.vo.LeadDeptVO;

import java.util.List;

/**
 * <p>
 * 公司领导主管部门设置 服务类
 * </p>
 *
 * @author 作者
 * @since 2021-12-04
 */
public interface LeadDeptService extends IService<LeadDept> {
    boolean add(LeadDeptVO leadDeptVO);
    boolean edit(LeadDeptVO leadDeptVO);
    boolean delete(List<String> userNameList);
}
