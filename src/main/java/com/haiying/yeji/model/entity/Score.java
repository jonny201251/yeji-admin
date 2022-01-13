package com.haiying.yeji.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 评分
 * </p>
 *
 * @author 作者
 * @since 2021-12-12
 */
@Getter
@Setter
public class Score implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 考核年份
     */
    private Integer year;

    /**
     * 评分类别,部门（行政评分）,党支部（党务评分）
     */
    private String scoreType;
    private String partyName;

    /**
     * 被评分人部门
     */
    private Integer depttId;

    private String depttName;
    /**
     * 被评分人部门排序
     */
    private Double depttSort;

    /**
     * 被评分人姓名
     */
    private String userrName;

    /**
     * 被评分人类型
     */
    private String userrType;

    private String userrRole;

    /**
     * 被评分人排序
     */
    private Double userrSort;

    /**
     * 被评分人类型：sys_dic.被考核对象
     */
    private String checkkObject;
    private Double checkkObjectSort;

    private Double score0;
    private Double score1;

    private Double score2;

    private Double score3;

    private Double score4;

    private Double score5;

    private Double score6;

    private Double score7;

    private Double score8;

    private Double score9;

    private Double totalScore;

    /**
     * 评分人部门
     */
    private Integer deptId;

    private String deptName;

    private Double deptSort;

    /**
     * 评分人姓名
     */
    private String userName;

    /**
     * 评分人的类型
     */
    private String userType;

    private String userRole;

    /**
     * 评分人类型：sys_dic.考核人员类型
     */
    private String checkUserType;

    //考核人员类型的排序
    private Double checkUserTypeSort;

    /**
     * 权重
     */
    private Double weight;
    /**
     * 评分状态：未评分，已评分
     */
    private String status;

    //述职材料
    @TableField(exist = false)
    private String diskName;

}
