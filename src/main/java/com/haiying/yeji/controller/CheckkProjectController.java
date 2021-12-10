package com.haiying.yeji.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haiying.yeji.common.result.ResponseResultWrapper;
import com.haiying.yeji.model.entity.CheckkProject;
import com.haiying.yeji.model.vo.CheckkProjectVO;
import com.haiying.yeji.service.CheckkProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 被考核的项目 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2021-12-09
 */
@RestController
@RequestMapping("/checkkProject")
@ResponseResultWrapper
public class CheckkProjectController {
    @Autowired
    CheckkProjectService checkkProjectService;

    @GetMapping("list")
    public IPage<CheckkProject> list(int current, int pageSize) {
        QueryWrapper<CheckkProject> wrapper = new QueryWrapper<CheckkProject>().select("distinct checkk_type");
        return checkkProjectService.page(new Page<>(current, pageSize), wrapper);
    }

    @PostMapping("add")
    public boolean add(@RequestBody CheckkProjectVO checkkProjectVO) {
        for (CheckkProject checkkProject : checkkProjectVO.getCheckList()) {
            checkkProject.setCheckkType(checkkProjectVO.getCheckkType());
        }
        return checkkProjectService.saveBatch(checkkProjectVO.getCheckList());
    }

    @GetMapping("get")
    public CheckkProjectVO get(String checkkType) {
        CheckkProjectVO checkkProjectVO = new CheckkProjectVO();
        checkkProjectVO.setCheckkType(checkkType);
        List<CheckkProject> list = checkkProjectService.list(new LambdaQueryWrapper<CheckkProject>().eq(CheckkProject::getCheckkType, checkkType));
        checkkProjectVO.setCheckList(list);
        return checkkProjectVO;
    }

    @PostMapping("edit")
    public boolean edit(@RequestBody CheckkProjectVO checkkProjectVO) {
        //先删除
        checkkProjectService.remove(new LambdaQueryWrapper<CheckkProject>().eq(CheckkProject::getCheckkType, checkkProjectVO.getCheckkType()));
        //后插入
        add(checkkProjectVO);
        return true;
    }

    @GetMapping("delete")
    public boolean delete(String[] arr) {
        List<String> list = Stream.of(arr).collect(Collectors.toList());
        return checkkProjectService.remove(new LambdaQueryWrapper<CheckkProject>().in(CheckkProject::getCheckkType, list));
    }
}
