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
 * @since 2021-12-05
 */
@Getter
@Setter
@TableName("party_lead2")
public class PartyLead2 implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer partyLeadId;

    /**
     * 党支部名称
     */
    private String partyName;


}
