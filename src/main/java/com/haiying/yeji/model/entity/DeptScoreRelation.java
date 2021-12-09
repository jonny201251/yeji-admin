package com.haiying.yeji.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 部门评分关系
 * </p>
 *
 * @author 作者
 * @since 2021-12-08
 */
@Getter
@Setter
@TableName("dept_score_relation")
public class DeptScoreRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 评分部门id
     */
    private Integer scoreDeptId;
    /**
     * 被评分部门
     */
    private Integer scoreeDeptId;


}
