package com.haiying.yeji.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 业务表的id和sort的最大值
 * </p>
 *
 * @author 作者
 * @since 2021-11-17
 */
@Getter
@Setter
@TableName("sys_count")
public class SysCount implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String entityName;

    private Integer sortCount;

    private String idCount;


}
