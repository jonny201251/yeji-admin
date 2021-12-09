package com.haiying.yeji.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haiying.yeji.common.result.ResponseResultWrapper;
import com.haiying.yeji.model.entity.ChargePartyLeader;
import com.haiying.yeji.model.vo.ChargePartyLeaderVO;
import com.haiying.yeji.service.ChargePartyLeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 主管党支部领导 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2021-12-09
 */
@RestController
@RequestMapping("/chargePartyLeader")
@ResponseResultWrapper
public class ChargePartyLeaderController {
    @Autowired
    ChargePartyLeaderService chargePartyLeaderService;

    @GetMapping("list")
    public IPage<ChargePartyLeader> list(int current, int pageSize) {
        QueryWrapper<ChargePartyLeader> wrapper = new QueryWrapper<ChargePartyLeader>().select("distinct user_name");
        return chargePartyLeaderService.page(new Page<>(current, pageSize), wrapper);
    }

    @PostMapping("add")
    public boolean add(@RequestBody ChargePartyLeaderVO chargePartyLeaderVO) {
        List<ChargePartyLeader> list = new ArrayList<>();
        for (String partyName : chargePartyLeaderVO.getPartyNameList()) {
            ChargePartyLeader chargePartyLeader = new ChargePartyLeader();
            chargePartyLeader.setUserName(chargePartyLeaderVO.getUserName());
            chargePartyLeader.setPartyName(partyName);
            list.add(chargePartyLeader);
        }
        return chargePartyLeaderService.saveBatch(list);
    }

    @GetMapping("get")
    public ChargePartyLeaderVO get(String userName) {
        List<ChargePartyLeader> list = chargePartyLeaderService.list(new LambdaQueryWrapper<ChargePartyLeader>().eq(ChargePartyLeader::getUserName, userName));
        ChargePartyLeaderVO chargePartyLeaderVO = new ChargePartyLeaderVO();
        chargePartyLeaderVO.setUserName(userName);
        chargePartyLeaderVO.setPartyNameList(list.stream().map(ChargePartyLeader::getPartyName).collect(Collectors.toList()));
        return chargePartyLeaderVO;
    }

    @PostMapping("edit")
    public boolean edit(@RequestBody ChargePartyLeaderVO chargePartyLeaderVO) {
        //先删除
        chargePartyLeaderService.remove(new LambdaQueryWrapper<ChargePartyLeader>().eq(ChargePartyLeader::getUserName, chargePartyLeaderVO.getUserName()));
        //后插入
        add(chargePartyLeaderVO);
        return true;
    }

    @GetMapping("delete")
    public boolean delete(String[] arr) {
        List<String> list = Stream.of(arr).collect(Collectors.toList());
        return chargePartyLeaderService.remove(new LambdaQueryWrapper<ChargePartyLeader>().in(ChargePartyLeader::getUserName, list));
    }
}
