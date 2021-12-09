package com.haiying.yeji.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class ChargeDeptLeaderVO {
    private String userName;
    private List<Integer> deptIdList;
}
