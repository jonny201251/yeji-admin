package com.haiying.yeji.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 被考核对象
 * </p>
 *
 * @author 作者
 * @since 2021-12-09
 */
@Getter
@Setter
@TableName("checkk_object")
public class CheckkObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 被考核对象
     */
    private String checkkObject;

    /**
     * 排序
     */
    private Double sort;

    /**
     * 考核人员类型
     */
    private String checkUserType;

    /**
     * 权重
     */
    private Double weight;


}
