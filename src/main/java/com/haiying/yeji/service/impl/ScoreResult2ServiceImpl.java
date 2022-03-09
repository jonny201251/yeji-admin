package com.haiying.yeji.service.impl;

import com.haiying.yeji.model.entity.ScoreResult2;
import com.haiying.yeji.mapper.ScoreResult2Mapper;
import com.haiying.yeji.service.ScoreResult2Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评分结果2，根据评分结果1，计算出7个考核项目的得分 服务实现类
 * </p>
 *
 * @author 作者
 * @since 2022-03-08
 */
@Service
public class ScoreResult2ServiceImpl extends ServiceImpl<ScoreResult2Mapper, ScoreResult2> implements ScoreResult2Service {

}
