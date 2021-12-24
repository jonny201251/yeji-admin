package com.haiying.yeji.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 述职材料
 * </p>
 *
 * @author 作者
 * @since 2021-12-24
 */
@Getter
@Setter
public class Upload implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer year;

    private String name;

    private String userName;

    private String fileName;
    private String diskName;


}
