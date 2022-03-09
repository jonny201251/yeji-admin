package com.haiying.yeji.service.impl;

import com.haiying.yeji.model.entity.ScoreResult1;
import com.haiying.yeji.mapper.ScoreResult1Mapper;
import com.haiying.yeji.service.ScoreResult1Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评分结果1，计算出类似-部门正职领导：主管公司领导（35%）、其他公司领导（25%）、相关部门正副职领导（20%）、部门人员（20%）[排除特别人员]。横向显示 服务实现类
 * </p>
 *
 * @author 作者
 * @since 2022-03-08
 */
@Service
public class ScoreResult1ServiceImpl extends ServiceImpl<ScoreResult1Mapper, ScoreResult1> implements ScoreResult1Service {

}
