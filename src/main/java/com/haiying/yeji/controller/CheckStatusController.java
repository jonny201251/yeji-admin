package com.haiying.yeji.controller;


import com.haiying.yeji.common.result.ResponseResultWrapper;
import com.haiying.yeji.model.entity.CheckStatus;
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
@RequestMapping("/checkStatus")
@ResponseResultWrapper
public class CheckStatusController extends BaseController<CheckStatus>{

}
