package com.haiying.yeji.mapper;

import com.haiying.yeji.model.entity.ScoreResult1;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 评分结果1，计算出类似-部门正职领导：主管公司领导（35%）、其他公司领导（25%）、相关部门正副职领导（20%）、部门人员（20%）[排除特别人员]。横向显示 Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2022-03-08
 */
@Mapper
public interface ScoreResult1Mapper extends BaseMapper<ScoreResult1> {

}
