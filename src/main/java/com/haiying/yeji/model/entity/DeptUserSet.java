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
@TableName("dept_user_set")
public class DeptUserSet implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评分方向,单向,双向
     */
    @TableId(value = "score_direction", type = IdType.AUTO)
    private String scoreDirection;


}
