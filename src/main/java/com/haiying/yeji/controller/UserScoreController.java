package com.haiying.yeji.controller;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haiying.yeji.common.exception.PageTipException;
import com.haiying.yeji.common.result.ResponseResultWrapper;
import com.haiying.yeji.model.entity.CheckUser;
import com.haiying.yeji.model.entity.Score;
import com.haiying.yeji.model.vo.ScoreVO;
import com.haiying.yeji.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 评分 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2021-12-12
 */
@RestController
@RequestMapping("/userScore")
@ResponseResultWrapper
public class UserScoreController {
    @Autowired
    ScoreService scoreService;
    @Autowired
    HttpSession httpSession;

    @GetMapping("list")
    public IPage<Score> list(int current, int pageSize, String userrName, String checkkObject, String status) {
        LambdaQueryWrapper<Score> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotEmpty(checkkObject)) {
            wrapper.eq(Score::getCheckkObject, checkkObject);
        }
        if (ObjectUtil.isNotEmpty(userrName)) {
            wrapper.like(Score::getUserrName, userrName);
        }
        if (ObjectUtil.isNotEmpty(status)) {
            wrapper.eq(Score::getStatus, status);
        }
        CheckUser user = (CheckUser) httpSession.getAttribute("user");
        wrapper.eq(Score::getUserName, user.getName());
        return scoreService.page(new Page<>(current, pageSize), wrapper);
    }

    @PostMapping("edit")
    public boolean edit(@RequestBody ScoreVO scoreVO) {
        return scoreService.updateBatchById(scoreVO.getScoreList());
    }

    @GetMapping("getScoreList")
    public List<Score> getScoreList() {
        CheckUser user = (CheckUser) httpSession.getAttribute("user");
        if (user == null) {
            throw new PageTipException("用户未登录");
        }
        return scoreService.list(new LambdaQueryWrapper<Score>().eq(Score::getUserName, user.getName()));
    }
}
