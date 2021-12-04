package com.haiying.yeji.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 主管党支部的党委领导设置
 * </p>
 *
 * @author 作者
 * @since 2021-12-04
 */
@Getter
@Setter
@TableName("party_lead")
public class PartyLead implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 登录名称
     */
    @TableId(value = "user_name", type = IdType.AUTO)
    private String userName;


}
