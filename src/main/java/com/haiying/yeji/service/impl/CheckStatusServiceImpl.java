package com.haiying.yeji.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haiying.yeji.mapper.CheckStatusMapper;
import com.haiying.yeji.model.entity.CheckStatus;
import com.haiying.yeji.model.entity.Score;
import com.haiying.yeji.service.CheckStatusService;
import com.haiying.yeji.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 考核启动和停止设置 服务实现类
 * </p>
 *
 * @author 作者
 * @since 2021-12-04
 */
@Service
public class CheckStatusServiceImpl extends ServiceImpl<CheckStatusMapper, CheckStatus> implements CheckStatusService {
    @Autowired
    ScoreService scoreService;

    @Override
    public boolean add(CheckStatus checkStatus) {
        this.save(checkStatus);
        scoreService.generate(checkStatus.getYear());
        return true;
    }

    @Override
    public boolean edit(CheckStatus checkStatus) {
        this.updateById(checkStatus);
        scoreService.remove(new LambdaQueryWrapper<Score>().eq(Score::getYear, checkStatus.getYear()));
        scoreService.generate(checkStatus.getYear());
        return true;
    }
}
