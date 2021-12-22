package com.haiying.yeji.controller;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haiying.yeji.common.result.ResponseResultWrapper;
import com.haiying.yeji.model.entity.Score;
import com.haiying.yeji.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 评分 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2021-12-12
 */
@RestController
@RequestMapping("/score")
@ResponseResultWrapper
public class ScoreController {
    @Autowired
    ScoreService scoreService;

    @GetMapping("list")
    public IPage<Score> list(int current, int pageSize,
                             Integer depttId, String checkkObject, String userrName, String scoreType,
                             Integer deptId, String checkUserType, String userRole, String userName, String status) {
        LambdaQueryWrapper<Score> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotEmpty(depttId)) {
            wrapper.eq(Score::getDepttId, depttId);
        }
        if (ObjectUtil.isNotEmpty(checkkObject)) {
            wrapper.eq(Score::getCheckkObject, checkkObject);
        }
        if (ObjectUtil.isNotEmpty(userrName)) {
            wrapper.like(Score::getUserrName, userrName);
        }
        if (ObjectUtil.isNotEmpty(scoreType)) {
            wrapper.eq(Score::getScoreType, scoreType);
        }
        if (ObjectUtil.isNotEmpty(deptId)) {
            wrapper.eq(Score::getDeptId, deptId);
        }
        if (ObjectUtil.isNotEmpty(checkUserType)) {
            wrapper.eq(Score::getCheckUserType, checkUserType);
        }
        if (ObjectUtil.isNotEmpty(userRole)) {
            wrapper.eq(Score::getUserRole, userRole);
        }
        if (ObjectUtil.isNotEmpty(userName)) {
            wrapper.like(Score::getUserName, userName);
        }
        if (ObjectUtil.isNotEmpty(status)) {
            wrapper.eq(Score::getStatus, status);
        }
//        wrapper.orderByAsc(Arrays.asList(Score::getCheckkObjectSort, Score::getCheckUserTypeSort));
        return scoreService.page(new Page<>(current, pageSize), wrapper);
    }

    @GetMapping("delete")
    public boolean delete(Integer[] arr) {
        List<Integer> idList = Stream.of(arr).collect(Collectors.toList());
        return scoreService.removeByIds(idList);
    }

    @GetMapping("getScoreList")
    public List<Score> getScoreList() {
        return scoreService.list(new LambdaQueryWrapper<Score>().eq(Score::getUserName, "朱佳"));
    }

    @GetMapping("generate")
    public boolean generate() {
        scoreService.generate(2021);
        return true;
    }
}
