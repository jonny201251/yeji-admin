package com.haiying.yeji.model.vo;

import com.haiying.yeji.model.entity.LeadDept2;
import lombok.Data;

import java.util.List;

@Data
public class LeadDeptVO {
    private String userName;
    private List<LeadDept2> list;
}
