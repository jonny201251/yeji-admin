package com.haiying.yeji.model.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

@Data
public class ScoreResult22Excel {
    @ExcelProperty("被评人部门")
    @ColumnWidth(20)
    private String depttName;
    @ExcelProperty("被评人姓名")
    @ColumnWidth(15)
    private String userrName;
    @ExcelProperty("被评人类型")
    @ColumnWidth(20)
    private String checkkObject;
    @ExcelProperty("政治素质")
    private Double score0;
    @ExcelProperty("职业素养")
    private Double score1;
    @ExcelProperty("廉洁从业")
    private Double score2;
    @ExcelProperty("决策能力")
    private Double score3;
    @ExcelProperty("执行能力")
    private Double score4;
    @ExcelProperty("创新能力")
    private Double score5;
    @ExcelProperty("工作业绩")
    private Double score6;
    @ExcelProperty("得分")
    private Double totalScore;
}
