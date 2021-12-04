package com.haiying.yeji.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 评分部门人员的单向和双向设置
 * </p>
 *
 * @author 作者
 * @since 2021-12-04
 */
@Getter
@Setter
@TableName("dept_user_set2")
public class DeptUserSet2 implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 评分方向,单向,双向
     */
    private String scoreDirection;

    /**
     * 评分部门
     */
    private Integer scoreDeptId;

    /**
     * 被评分部门
     */
    private Integer scoreeDeptId;


}
