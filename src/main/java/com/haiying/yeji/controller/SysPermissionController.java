package com.haiying.yeji.controller;


import com.haiying.yeji.common.result.ResponseResultWrapper;
import com.haiying.yeji.model.entity.SysPermission;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2021-12-01
 */
@RestController
@RequestMapping("/sysPermission")
@ResponseResultWrapper
public class SysPermissionController extends BaseTreeController<SysPermission> {

}
