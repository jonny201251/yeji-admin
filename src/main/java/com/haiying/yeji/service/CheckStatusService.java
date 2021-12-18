package com.haiying.yeji.service;

import com.haiying.yeji.model.entity.CheckStatus;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 考核启动和停止设置 服务类
 * </p>
 *
 * @author 作者
 * @since 2021-12-04
 */
public interface CheckStatusService extends IService<CheckStatus> {
    boolean add(CheckStatus checkStatus);
    boolean edit(CheckStatus checkStatus);
}
