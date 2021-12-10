package com.haiying.yeji.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 党委书记
 * </p>
 *
 * @author 作者
 * @since 2021-12-10
 */
@Getter
@Setter
@TableName("party_secretary")
public class PartySecretary implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 类别,党委书记，党委副书记
     */
    private String type;

    /**
     * 姓名
     */
    private String userName;


}
