package com.haiying.yeji.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 被考核的人员类型的考核项目设置
 * </p>
 *
 * @author 作者
 * @since 2021-12-04
 */
@Getter
@Setter
@TableName("user_checkk_project")
public class UserCheckkProject implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 考核人员类型
     */
    @TableId(value = "user_type", type = IdType.AUTO)
    private String userType;

    /**
     * 备足
     */
    private String remark;


}
