package com.haiying.yeji.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 党支部与部门的设置
 * </p>
 *
 * @author 作者
 * @since 2021-12-04
 */
@Getter
@Setter
@TableName("party_dept_set")
public class PartyDeptSet implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 党支部名称
     */
    @TableId(value = "party_name", type = IdType.AUTO)
    private String partyName;


}
