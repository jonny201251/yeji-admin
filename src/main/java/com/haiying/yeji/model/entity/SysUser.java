package com.haiying.yeji.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author 作者
 * @since 2021-12-01
 */
@Getter
@Setter
@TableName("sys_user")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 登录名称
     */
    private String loginName;

    /**
     * 显示名称
     */
    private String displayName;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 性别
     */
    private String gender;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态，值为启用和停用
     */
    private String status;

    /**
     * 排序
     */
    private Double sort;

    /**
     * 备注
     */
    private String remark;

    /**
     * 部门id
     */
    private Integer deptId;


}
