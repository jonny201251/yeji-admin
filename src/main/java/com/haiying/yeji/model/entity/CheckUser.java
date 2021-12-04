package com.haiying.yeji.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 考核人员信息设置
 * </p>
 *
 * @author 作者
 * @since 2021-12-04
 */
@Getter
@Setter
@TableName("check_user")
public class CheckUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 登录名称
     */
    private String name;

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
     * 人员角色
     */
    private String userRole;

    /**
     * 班组id
     */
    private Integer groupId;

    /**
     * 党支部名称
     */
    private String partyName;

    /**
     * 党支部角色
     */
    private String partyRole;

    /**
     * 人员排序
     */
    private Double sort;

    /**
     * 备注
     */
    private String remark;


}
