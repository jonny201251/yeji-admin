package com.haiying.yeji.model.vo;

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
public class ScoreVO implements Serializable {

    private Integer id;
    private String depttName;
    private String userrName;
    private String checkkObject;

    private Double score1;

    private Double score2;

    private Double score3;

    private Double score4;

    private Double score5;

    private Double score6;

    private Double score7;

    private Double totalScore;

    private String userName;
    private String checkUserType;
    private String status;


}
