package com.haiying.yeji.controller;


import com.haiying.yeji.common.result.ResponseResultWrapper;
import com.haiying.yeji.model.entity.SysRole;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 角色 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2021-12-01
 */
@RestController
@RequestMapping("/sysRole")
@ResponseResultWrapper
public class SysRoleController extends BaseController<SysRole> {

}
