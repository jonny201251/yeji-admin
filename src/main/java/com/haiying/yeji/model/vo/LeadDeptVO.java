package com.haiying.yeji.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class LeadDeptVO {
    private String userName;
    private List<Integer> deptIdList;
}
