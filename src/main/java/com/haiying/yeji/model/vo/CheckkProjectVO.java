package com.haiying.yeji.model.vo;

import com.haiying.yeji.model.entity.CheckkProject;
import lombok.Data;

import java.util.List;

@Data
public class CheckkProjectVO {
    private String checkkObject;
    private String oldCheckkObject;
    private List<CheckkProject> checkList;
}
