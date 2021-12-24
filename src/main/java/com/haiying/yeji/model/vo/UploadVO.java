package com.haiying.yeji.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class UploadVO {
    private Integer year;
    private String name;
    private List<FileVO> uploadList;
}
