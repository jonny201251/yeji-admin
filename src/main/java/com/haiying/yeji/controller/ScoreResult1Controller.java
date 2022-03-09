package com.haiying.yeji.controller;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haiying.yeji.common.result.ResponseResultWrapper;
import com.haiying.yeji.model.entity.ScoreResult1;
import com.haiying.yeji.service.ScoreResult1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 评分结果1，计算出类似-部门正职领导：主管公司领导（35%）、其他公司领导（25%）、相关部门正副职领导（20%）、部门人员（20%）[排除特别人员]。横向显示 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2022-03-08
 */
@RestController
@RequestMapping("/scoreResult1")
@ResponseResultWrapper
public class ScoreResult1Controller {

    @Autowired
    ScoreResult1Service scoreResult1Service;

    @GetMapping("list")
    public IPage<ScoreResult1> list(int current, int pageSize, String userrName, String depttName,
                                    String scoreType, String checkkObject, String checkUserType, String good) {
        LambdaQueryWrapper<ScoreResult1> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotEmpty(userrName)) {
            wrapper.like(ScoreResult1::getUserrName, userrName);
        }
        if (ObjectUtil.isNotEmpty(depttName)) {
            wrapper.eq(ScoreResult1::getDepttName, depttName);
        }
        if (ObjectUtil.isNotEmpty(scoreType)) {
            wrapper.eq(ScoreResult1::getScoreType, scoreType);
        }
        if (ObjectUtil.isNotEmpty(checkkObject)) {
            wrapper.eq(ScoreResult1::getCheckkObject, checkkObject);
        }
        if (ObjectUtil.isNotEmpty(checkUserType)) {
            wrapper.eq(ScoreResult1::getCheckUserType, checkUserType);
        }
        if ("是".equals(good)) {
            wrapper.gt(ScoreResult1::getTotalScore, 90);
        }
        if ("否".equals(good)) {
            wrapper.lt(ScoreResult1::getTotalScore, 90);
        }
        return scoreResult1Service.page(new Page<>(current, pageSize), wrapper);
    }
}
