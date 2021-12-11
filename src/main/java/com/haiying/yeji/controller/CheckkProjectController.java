package com.haiying.yeji.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haiying.yeji.common.result.ResponseResultWrapper;
import com.haiying.yeji.model.entity.CheckkProject;
import com.haiying.yeji.model.entity.SysDic;
import com.haiying.yeji.model.vo.CheckkProjectVO;
import com.haiying.yeji.service.CheckkProjectService;
import com.haiying.yeji.service.SysDicService;
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
    @Autowired
    SysDicService sysDicService;

    @GetMapping("list")
    public IPage<CheckkProject> list(int current, int pageSize) {
        QueryWrapper<CheckkProject> wrapper = new QueryWrapper<CheckkProject>().select("distinct checkk_object").orderByAsc("sort");
        return checkkProjectService.page(new Page<>(current, pageSize), wrapper);
    }

    @PostMapping("add")
    public boolean add(@RequestBody CheckkProjectVO checkkProjectVO) {
        //先删除
        checkkProjectService.remove(new LambdaQueryWrapper<CheckkProject>().eq(CheckkProject::getCheckkObject, checkkProjectVO.getOldCheckkObject()));
        //后插入
        SysDic sysDic = sysDicService.getOne(new LambdaQueryWrapper<SysDic>().eq(SysDic::getFlag, "被考核对象").eq(SysDic::getName, checkkProjectVO.getCheckkObject()));
        for (CheckkProject checkkProject : checkkProjectVO.getCheckList()) {
            //edit
            checkkProject.setId(null);

            checkkProject.setCheckkObject(checkkProjectVO.getCheckkObject());
            checkkProject.setSort(sysDic.getSort());
        }
        return checkkProjectService.saveBatch(checkkProjectVO.getCheckList());
    }

    @GetMapping("get")
    public CheckkProjectVO get(String checkkObject) {
        CheckkProjectVO checkkProjectVO = new CheckkProjectVO();
        List<CheckkProject> list = checkkProjectService.list(new LambdaQueryWrapper<CheckkProject>().eq(CheckkProject::getCheckkObject, checkkObject));
        checkkProjectVO.setCheckList(list);
        checkkProjectVO.setCheckkObject(checkkObject);
        checkkProjectVO.setOldCheckkObject(checkkObject);
        return checkkProjectVO;
    }

    @PostMapping("edit")
    public boolean edit(@RequestBody CheckkProjectVO checkkProjectVO) {
        //先删除
        checkkProjectService.remove(new LambdaQueryWrapper<CheckkProject>().eq(CheckkProject::getCheckkObject, checkkProjectVO.getOldCheckkObject()));
        //后插入
        return add(checkkProjectVO);
    }

    @GetMapping("delete")
    public boolean delete(String[] arr) {
        List<String> list = Stream.of(arr).collect(Collectors.toList());
        return checkkProjectService.remove(new LambdaQueryWrapper<CheckkProject>().in(CheckkProject::getCheckkObject, list));
    }

    @GetMapping("copy")
    public boolean copy(String checkkObject) {
        List<CheckkProject> list = checkkProjectService.list(new LambdaQueryWrapper<CheckkProject>().eq(CheckkProject::getCheckkObject, checkkObject));
        for (CheckkProject checkkProject : list) {
            checkkProject.setId(null);
            checkkProject.setCheckkObject(checkkProject.getCheckkObject() + "--副本");
        }
        return checkkProjectService.saveBatch(list);
    }
}
