package com.haiying.yeji.service;

import com.haiying.yeji.model.entity.SysDept;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @author 作者
 * @since 2021-12-01
 */
public interface SysDeptService extends IService<SysDept> {
    boolean edit(SysDept sysDept);
}
