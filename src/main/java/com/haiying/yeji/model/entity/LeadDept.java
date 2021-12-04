package com.haiying.yeji.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 公司领导主管部门设置
 * </p>
 *
 * @author 作者
 * @since 2021-12-04
 */
@Getter
@Setter
@TableName("lead_dept")
public class LeadDept implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 登录名称
     */
    @TableId(value = "user_name", type = IdType.AUTO)
    private String userName;


}
