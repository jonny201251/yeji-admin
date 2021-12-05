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
@TableName("party_dept2")
public class PartyDept2 implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer partyDeptId;

    private Integer deptId;


}
