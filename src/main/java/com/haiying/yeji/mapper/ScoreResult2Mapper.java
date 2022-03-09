package com.haiying.yeji.mapper;

import com.haiying.yeji.model.entity.ScoreResult2;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 评分结果2，根据评分结果1，计算出7个考核项目的得分 Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2022-03-08
 */
@Mapper
public interface ScoreResult2Mapper extends BaseMapper<ScoreResult2> {

}
