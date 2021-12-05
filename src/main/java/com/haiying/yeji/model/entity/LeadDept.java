package com.haiying.yeji.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

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
     * 登录姓名
     */
    private String userName;
    /**
     * 人员排序
     */
    private Double sort;
}
