package com.haiying.yeji.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haiying.yeji.common.result.ResponseResultWrapper;
import com.haiying.yeji.model.entity.Party;
import com.haiying.yeji.model.vo.LabelValue;
import com.haiying.yeji.model.vo.PartyVO;
import com.haiying.yeji.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 党支部 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2021-12-09
 */
@RestController
@RequestMapping("/party")
@ResponseResultWrapper
public class PartyController {
    @Autowired
    PartyService partyService;

    @GetMapping("list")
    public IPage<Party> list(int current, int pageSize) {
        QueryWrapper<Party> wrapper = new QueryWrapper<Party>().select("distinct party_name");
        return partyService.page(new Page<>(current, pageSize), wrapper);
    }

    @PostMapping("add")
    public boolean add(@RequestBody PartyVO partyVO) {
        List<Party> list = new ArrayList<>();
        for (Integer deptId : partyVO.getDeptIdList()) {
            Party party = new Party();
            party.setPartyName(partyVO.getPartyName());
            party.setDeptId(deptId);
            list.add(party);
        }
        return partyService.saveBatch(list);
    }

    @GetMapping("get")
    public PartyVO get(String partyName) {
        List<Party> list = partyService.list(new LambdaQueryWrapper<Party>().eq(Party::getPartyName, partyName));
        PartyVO partyVO = new PartyVO();
        partyVO.setPartyName(partyName);
        partyVO.setDeptIdList(list.stream().map(Party::getDeptId).collect(Collectors.toList()));
        return partyVO;
    }

    @PostMapping("edit")
    public boolean edit(@RequestBody PartyVO partyVO) {
        //先删除
        partyService.remove(new LambdaQueryWrapper<Party>().eq(Party::getPartyName, partyVO.getPartyName()));
        //后插入
        add(partyVO);
        return true;
    }

    @GetMapping("delete")
    public boolean delete(String[] arr) {
        List<String> list = Stream.of(arr).collect(Collectors.toList());
        return partyService.remove(new LambdaQueryWrapper<Party>().in(Party::getPartyName, list));
    }

    @GetMapping("getLabelValue")
    public List<LabelValue> getLabelValue() {
        List<Party> list=partyService.list(new QueryWrapper<Party>().select("distinct party_name"));
        return list.stream().map(item -> new LabelValue(item.getPartyName(), item.getPartyName())).collect(Collectors.toList());
    }
}
