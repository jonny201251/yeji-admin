package com.haiying.yeji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haiying.yeji.common.result.ResponseResultWrapper;
import com.haiying.yeji.model.entity.DeptScoreRelation;
import com.haiying.yeji.model.vo.DeptScoreRelationVO;
import com.haiying.yeji.service.DeptScoreRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 部门评分关系 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2021-12-08
 */
@RestController
@RequestMapping("/deptScoreRelation")
@ResponseResultWrapper
public class DeptScoreRelationController {
    @Autowired
    DeptScoreRelationService deptScoreRelationService;

    @GetMapping("list")
    public IPage<DeptScoreRelation> list(int current, int pageSize) {
        QueryWrapper<DeptScoreRelation> wrapper = new QueryWrapper<DeptScoreRelation>().select("distinct score_dept_id");
        return deptScoreRelationService.page(new Page<>(current, pageSize), wrapper);
    }

    @PostMapping("add")
    public boolean add(@RequestBody DeptScoreRelationVO deptScoreRelationVO) {
        List<DeptScoreRelation> list = new ArrayList<>();
        for (Integer scoreeDeptId : deptScoreRelationVO.getScoreeDeptIdList()) {
            DeptScoreRelation deptScoreRelation = new DeptScoreRelation();
            deptScoreRelation.setScoreDeptId(deptScoreRelationVO.getScoreDeptId());
            deptScoreRelation.setScoreeDeptId(scoreeDeptId);
            list.add(deptScoreRelation);
        }
        return deptScoreRelationService.saveBatch(list);
    }

    @GetMapping("get")
    public DeptScoreRelationVO get(Integer scoreDeptId) {
        List<DeptScoreRelation> list = deptScoreRelationService.list(new LambdaQueryWrapper<DeptScoreRelation>().eq(DeptScoreRelation::getScoreDeptId, scoreDeptId));
        DeptScoreRelationVO deptScoreRelationVO = new DeptScoreRelationVO();
        deptScoreRelationVO.setScoreDeptId(scoreDeptId);
        deptScoreRelationVO.setScoreeDeptIdList(list.stream().map(DeptScoreRelation::getScoreeDeptId).collect(Collectors.toList()));
        return deptScoreRelationVO;
    }

    @PostMapping("edit")
    public boolean edit(@RequestBody DeptScoreRelationVO deptScoreRelationVO) {
        //先删除
        deptScoreRelationService.remove(new LambdaQueryWrapper<DeptScoreRelation>().eq(DeptScoreRelation::getScoreDeptId, deptScoreRelationVO.getScoreDeptId()));
        //后插入
        add(deptScoreRelationVO);
        return true;
    }

    @GetMapping("delete")
    public boolean delete(Integer[] arr) {
        List<Integer> list = Stream.of(arr).collect(Collectors.toList());
        return deptScoreRelationService.remove(new LambdaQueryWrapper<DeptScoreRelation>().in(DeptScoreRelation::getScoreDeptId, list));
    }
}
