package com.haiying.yeji.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.haiying.yeji.model.entity.Score;

/**
 * <p>
 * 评分 服务类
 * </p>
 *
 * @author 作者
 * @since 2021-12-12
 */
public interface ScoreService extends IService<Score> {
    boolean generate(Integer year);

}
