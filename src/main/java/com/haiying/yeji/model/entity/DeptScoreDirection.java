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
@TableName("dept_score_direction")
public class DeptScoreDirection implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 评分方向,单向,双向
     */
    private String direction;


}
