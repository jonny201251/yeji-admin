package com.haiying.yeji.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 部门表
 * </p>
 *
 * @author 作者
 * @since 2021-12-01
 */
@Getter
@Setter
@TableName("sys_dept")
public class SysDept implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 父级节点
     */
    private Integer pid;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 部门编码
     */
    private String code;

    /**
     * 状态，值为启用和停用
     */
    private String status;

    /**
     * 根据该字段进行排序显示
     */
    private Double sort;

    /**
     * 备注
     */
    private String remark;

    @TableField(exist = false)
    private List<SysDept> children;
}
