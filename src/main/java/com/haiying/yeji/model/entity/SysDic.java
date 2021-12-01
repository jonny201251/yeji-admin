package com.haiying.yeji.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 数据字典
 * </p>
 *
 * @author 作者
 * @since 2021-11-17
 */
@Getter
@Setter
@TableName("sys_dic")
public class SysDic implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 大类名称
     */
    private String flag;

    /**
     * 小类名称
     */
    private String name;

    /**
     * 状态，值为启用和停用
     */
    private String status;

    /**
     * 排序
     */
    private Double sort;


}
