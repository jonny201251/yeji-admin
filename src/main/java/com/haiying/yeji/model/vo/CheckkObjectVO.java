package com.haiying.yeji.model.vo;

import com.haiying.yeji.model.entity.CheckkObject;
import lombok.Data;

import java.util.List;

@Data
public class CheckkObjectVO {
    private String checkkObject;
    private String oldCheckkObject;
    private List<CheckkObject> checkList;
}
