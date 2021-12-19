package com.haiying.yeji.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.haiying.yeji.model.entity.DeptGroup;

/**
 * <p>
 * 班组 服务类
 * </p>
 *
 * @author 作者
 * @since 2021-12-09
 */
public interface DeptGroupService extends IService<DeptGroup> {
    boolean edit(DeptGroup group);
}
