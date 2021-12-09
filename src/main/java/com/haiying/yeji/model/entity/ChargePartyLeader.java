package com.haiying.yeji.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 主管党支部领导
 * </p>
 *
 * @author 作者
 * @since 2021-12-09
 */
@Getter
@Setter
@TableName("charge_party_leader")
public class ChargePartyLeader implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 党支部名称
     */
    private String partyName;


}
