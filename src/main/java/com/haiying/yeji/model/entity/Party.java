package com.haiying.yeji.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 党支部
 * </p>
 *
 * @author 作者
 * @since 2021-12-09
 */
@Getter
@Setter
public class Party implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 党支部名称
     */
    private String partyName;

    /**
     * 党支部下的部门id
     */
    private Integer deptId;


}
