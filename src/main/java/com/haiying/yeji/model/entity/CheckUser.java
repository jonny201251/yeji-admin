package com.haiying.yeji.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 考核人员信息
 * </p>
 *
 * @author 作者
 * @since 2021-12-12
 */
@Getter
@Setter
@TableName("check_user")
public class CheckUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 登录姓名
     */
    private String name;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 性别
     */
    private String gender;

    /**
     * 是否党员
     */
    private String havePartyMember;

    /**
     * 工作状态,在岗,离职
     */
    private String workStatus;

    /**
     * 人员类型,一般人员,中层领导,公司领导
     */
    private String userType;

    /**
     * 部门id
     */
    private Integer deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 部门排序
     */
    private Double deptSort;

    /**
     * 人员角色
     */
    private String userRole;

    /**
     * 班组id
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer groupId;
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String groupName;

    /**
     * 党支部名称
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String partyName;

    /**
     * 党支部角色
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String partyRole;

    /**
     * 人员排序
     */
    private Double userSort;

    /**
     * 备注
     */
    private String remark;


}
