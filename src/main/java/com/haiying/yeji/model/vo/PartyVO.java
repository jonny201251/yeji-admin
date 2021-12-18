package com.haiying.yeji.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class PartyVO {
    private String partyName;
    private List<Integer> deptIdList;
    private Double sort;
}
