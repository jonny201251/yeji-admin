package com.haiying.yeji.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 评分结果1，计算出类似-部门正职领导：主管公司领导（35%）、其他公司领导（25%）、相关部门正副职领导（20%）、部门人员（20%）[排除特别人员]。横向显示
 * </p>
 *
 * @author 作者
 * @since 2022-03-08
 */
@Getter
@Setter
@TableName("score_result1")
public class ScoreResult1 implements Serializable {

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

    /**
     * 总得分
     */
    private Double totalScore;

    /**
     * 评分人的类型
     */
    private String userType;

    private String userRole;

    /**
     * 评分人类型：sys_dic.考核人员类型
     */
    private String checkUserType;

    /**
     * 考核人员类型的排序
     */
    private String checkUserTypeSort;

    /**
     * 权重
     */
    private Double checkWeight;


}
