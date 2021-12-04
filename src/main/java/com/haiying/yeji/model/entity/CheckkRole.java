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
@TableName("checkk_role")
public class CheckkRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 被考核人员角色名称
     */
    @TableId(value = "checkk_role_name", type = IdType.AUTO)
    private String checkkRoleName;


}
