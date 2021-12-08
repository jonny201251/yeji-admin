package com.haiying.yeji.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 部门评分方向
 * </p>
 *
 * @author 作者
 * @since 2021-12-08
 */
@Getter
@Setter
@TableName("dept_score_direction2")
public class DeptScoreDirection2 implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer directionId;

    /**
     * 评分部门
     */
    private Integer scoreDeptId;

    /**
     * 被评分部门
     */
    private Integer scoreeDeptId;


}
