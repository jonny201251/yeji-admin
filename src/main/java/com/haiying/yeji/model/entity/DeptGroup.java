package com.haiying.yeji.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 班组
 * </p>
 *
 * @author 作者
 * @since 2021-12-09
 */
@Getter
@Setter
@TableName("dept_group")
public class DeptGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 部门id
     */
    private Integer deptId;

    private Double deptSort;

    /**
     * 班组名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;


}
