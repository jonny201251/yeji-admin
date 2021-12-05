package com.haiying.yeji.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haiying.yeji.common.result.ResponseResultWrapper;
import com.haiying.yeji.model.entity.PartyLead;
import com.haiying.yeji.model.entity.PartyLead2;
import com.haiying.yeji.model.vo.PartyLeadVO;
import com.haiying.yeji.service.PartyLead2Service;
import com.haiying.yeji.service.PartyLeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 主管党支部的党委领导设置 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2021-12-05
 */
@RestController
@RequestMapping("/partyLead")
@ResponseResultWrapper
public class PartyLeadController {
    @Autowired
    PartyLeadService partyLeadService;
    @Autowired
    PartyLead2Service partyLead2Service;

    @GetMapping("list")
    public IPage<PartyLead> list(int current, int pageSize) {
        LambdaQueryWrapper<PartyLead> wrapper = new LambdaQueryWrapper<>();
        return partyLeadService.page(new Page<>(current, pageSize), wrapper);
    }

    @PostMapping("add")
    public boolean add(@RequestBody PartyLeadVO partyLeadVO) {
        return partyLeadService.add(partyLeadVO);
    }

    @GetMapping("get")
    public PartyLeadVO getById(Integer id) {
        PartyLead partyLead = partyLeadService.getById(id);
        PartyLeadVO partyLeadVO = new PartyLeadVO();
        partyLeadVO.setUserName(partyLead.getUserName());
        List<PartyLead2> list = partyLead2Service.list(new LambdaQueryWrapper<PartyLead2>().eq(PartyLead2::getPartyLeadId, partyLead.getId()));
        partyLeadVO.setPartyNameList(list.stream().map(PartyLead2::getPartyName).collect(Collectors.toList()));
        return partyLeadVO;
    }

    @PostMapping("edit")
    public boolean edit(@RequestBody PartyLeadVO partyLeadVO) {
        return partyLeadService.edit(partyLeadVO);
    }

    @GetMapping("delete")
    public boolean delete(Integer[] idArr) {
        List<Integer> idList = Stream.of(idArr).collect(Collectors.toList());
        return partyLeadService.delete(idList);
    }
}
