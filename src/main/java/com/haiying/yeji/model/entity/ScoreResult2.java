package com.haiying.yeji.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 评分结果2，根据评分结果1，计算出7个考核项目的得分
 * </p>
 *
 * @author 作者
 * @since 2022-03-08
 */
@Getter
@Setter
@TableName("score_result2")
public class ScoreResult2 implements Serializable {

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

    private Double totalScore;

    private String userType;

    private String userRole;

    private String checkUserType;

    private Double checkWeight;

}
