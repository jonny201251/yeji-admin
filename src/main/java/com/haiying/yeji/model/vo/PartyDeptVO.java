package com.haiying.yeji.model.vo;

import lombok.Data;

import java.util.List;
@Data
public class PartyDeptVO {
    private String partyName;
    private List<Integer> deptIdList;
}
