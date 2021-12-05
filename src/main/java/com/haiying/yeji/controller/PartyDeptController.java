package com.haiying.yeji.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haiying.yeji.common.result.ResponseResultWrapper;
import com.haiying.yeji.model.entity.PartyDept;
import com.haiying.yeji.model.entity.PartyDept2;
import com.haiying.yeji.model.vo.LabelValue;
import com.haiying.yeji.model.vo.PartyDeptVO;
import com.haiying.yeji.service.PartyDept2Service;
import com.haiying.yeji.service.PartyDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 党支部与部门的设置 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2021-12-05
 */
@RestController
@RequestMapping("/partyDept")
@ResponseResultWrapper
public class PartyDeptController {
    @Autowired
    PartyDeptService partyDeptService;
    @Autowired
    PartyDept2Service partyDept2Service;


    @GetMapping("list")
    public IPage<PartyDept> list(int current, int pageSize) {
        LambdaQueryWrapper<PartyDept> wrapper = new LambdaQueryWrapper<>();
        return partyDeptService.page(new Page<>(current, pageSize), wrapper);
    }

    @PostMapping("add")
    public boolean add(@RequestBody PartyDeptVO partyDeptVO) {
        return partyDeptService.add(partyDeptVO);
    }

    @GetMapping("get")
    public PartyDeptVO getById(Integer id) {
        PartyDept partyDept = partyDeptService.getById(id);
        PartyDeptVO partyDeptVO = new PartyDeptVO();
        partyDeptVO.setPartyName(partyDept.getPartyName());
        List<PartyDept2> list = partyDept2Service.list(new LambdaQueryWrapper<PartyDept2>().eq(PartyDept2::getPartyDeptId, partyDept.getId()));
        partyDeptVO.setDeptIdList(list.stream().map(PartyDept2::getDeptId).collect(Collectors.toList()));
        return partyDeptVO;
    }

    @PostMapping("edit")
    public boolean edit(@RequestBody PartyDeptVO partyDeptVO) {
        return partyDeptService.edit(partyDeptVO);
    }

    @GetMapping("delete")
    public boolean delete(Integer[] idArr) {
        List<Integer> idList = Stream.of(idArr).collect(Collectors.toList());
        return partyDeptService.delete(idList);
    }

    @GetMapping("getLabelValue")
    public List<LabelValue> getLabelValue() {
        List<PartyDept> list = partyDeptService.list();
        return list.stream().map(item -> new LabelValue(item.getPartyName(), item.getPartyName())).collect(Collectors.toList());
    }
}
