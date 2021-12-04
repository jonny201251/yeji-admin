package com.haiying.yeji.controller;


import com.haiying.yeji.common.result.ResponseResultWrapper;
import com.haiying.yeji.model.entity.CheckSet;
import com.haiying.yeji.service.CheckSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 考核启动和停止设置 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2021-12-04
 */
@RestController
@RequestMapping("/checkSet")
@ResponseResultWrapper
public class CheckSetController extends BaseController<CheckSet> {
    @Autowired
    CheckSetService checkSetService;

/*    @PostMapping("add")
    public boolean add(@RequestBody CheckSet checkSet) {
        checkSet.setStartDatetime(LocalDateTime.now());
        return checkSetService.save(checkSet);
    }*/
}
