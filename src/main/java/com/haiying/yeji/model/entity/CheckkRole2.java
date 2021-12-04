package com.haiying.yeji.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 被考核的人员角色的设置
 * </p>
 *
 * @author 作者
 * @since 2021-12-04
 */
@Getter
@Setter
@TableName("checkk_role2")
public class CheckkRole2 implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 被考核人员角色
     */
    private String checkkRoleName;

    /**
     * 考核人员类型
     */
    private String checkUserType;

    /**
     * 权重
     */
    private Double weight;


}
