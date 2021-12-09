package com.haiying.yeji.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 主管部门领导
 * </p>
 *
 * @author 作者
 * @since 2021-12-09
 */
@Getter
@Setter
@TableName("charge_dept_leader")
public class ChargeDeptLeader implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 部门id
     */
    private Integer deptId;


}
