package com.haiying.yeji.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 党支部与部门的设置
 * </p>
 *
 * @author 作者
 * @since 2021-12-05
 */
@Getter
@Setter
@TableName("party_dept")
public class PartyDept implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 党支部名称
     */
    private String partyName;


}
