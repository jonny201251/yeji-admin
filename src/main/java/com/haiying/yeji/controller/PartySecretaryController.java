package com.haiying.yeji.controller;


import com.haiying.yeji.common.result.ResponseResultWrapper;
import com.haiying.yeji.model.entity.PartySecretary;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 党委书记 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2021-12-10
 */
@RestController
@RequestMapping("/partySecretary")
@ResponseResultWrapper
public class PartySecretaryController extends BaseController<PartySecretary> {

}
