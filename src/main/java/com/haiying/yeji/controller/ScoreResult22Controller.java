package com.haiying.yeji.controller;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haiying.yeji.common.result.ResponseResultWrapper;
import com.haiying.yeji.model.entity.ScoreResult22;
import com.haiying.yeji.service.ScoreResult22Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 评分结果22，考核内容，即个人得分。 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2022-03-09
 */
@RestController
@RequestMapping("/scoreResult22")
@ResponseResultWrapper
public class ScoreResult22Controller {
    @Autowired
    ScoreResult22Service scoreResult22Service;

    @GetMapping("list")
    public IPage<ScoreResult22> list(int current, int pageSize, String userrName, String depttName,
                                     String scoreType, String checkkObject, String good) {
        LambdaQueryWrapper<ScoreResult22> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotEmpty(userrName)) {
            wrapper.like(ScoreResult22::getUserrName, userrName);
        }
        if (ObjectUtil.isNotEmpty(depttName)) {
            wrapper.eq(ScoreResult22::getDepttName, depttName);
        }
        if (ObjectUtil.isNotEmpty(scoreType)) {
            wrapper.eq(ScoreResult22::getScoreType, scoreType);
        }
        if (ObjectUtil.isNotEmpty(checkkObject)) {
            wrapper.eq(ScoreResult22::getCheckkObject, checkkObject);
        }
        if ("是".equals(good)) {
            wrapper.gt(ScoreResult22::getTotalScore, 90);
        }
        if ("否".equals(good)) {
            wrapper.lt(ScoreResult22::getTotalScore, 90);
        }
        return scoreResult22Service.page(new Page<>(current, pageSize), wrapper);
    }
}
