package com.haiying.yeji.controller;


import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haiying.yeji.common.exception.PageTipException;
import com.haiying.yeji.common.result.ResponseResultWrapper;
import com.haiying.yeji.model.entity.CheckUser;
import com.haiying.yeji.model.entity.Score;
import com.haiying.yeji.model.entity.ScoreResult22;
import com.haiying.yeji.model.entity.Upload;
import com.haiying.yeji.model.excel.ScoreExcel;
import com.haiying.yeji.model.excel.ScoreResult22Excel;
import com.haiying.yeji.model.vo.LabelValue;
import com.haiying.yeji.model.vo.ScoreVO;
import com.haiying.yeji.service.ScoreResult22Service;
import com.haiying.yeji.service.ScoreService;
import com.haiying.yeji.service.UploadService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 评分 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2021-12-12
 */
@RestController
@RequestMapping("/userScore")
@ResponseResultWrapper
public class UserScoreController {
    @Autowired
    ScoreService scoreService;
    @Autowired
    HttpSession httpSession;
    @Autowired
    UploadService uploadService;
    @Autowired
    ScoreResult22Service scoreResult22Service;

    @GetMapping("list")
    public IPage<Score> list(int current, int pageSize, String checkkObject, String userrName, String status) {
        LambdaQueryWrapper<Score> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotEmpty(checkkObject)) {
            wrapper.eq(Score::getCheckkObject, checkkObject);
        }
        if (ObjectUtil.isNotEmpty(userrName)) {
            wrapper.like(Score::getUserrName, userrName);
        }
        if (ObjectUtil.isNotEmpty(status)) {
            wrapper.eq(Score::getStatus, status);
        }
        CheckUser user = (CheckUser) httpSession.getAttribute("user");
        wrapper.eq(Score::getUserName, user.getName());
        IPage<Score> page = scoreService.page(new Page<>(current, pageSize), wrapper);
        //
        List<Score> list = page.getRecords();
        if (ObjectUtil.isNotEmpty(list)) {
            List<Upload> uploadList = uploadService.list(new LambdaQueryWrapper<Upload>().eq(Upload::getYear, 2021).in(Upload::getUserName, list.stream().map(Score::getUserrName).collect(Collectors.toList())));
            if (ObjectUtil.isNotEmpty(uploadList)) {
                Map<String, String> uploadMap = new HashMap<>();
                for (Upload upload : uploadList) {
                    uploadMap.put(upload.getUserName(), upload.getDiskName());
                }
                for (Score score : list) {
                    score.setDiskName(uploadMap.get(score.getUserrName()));
                }
            }
        }
        return page;
    }

    //批量评分
    @PostMapping("edit")
    public boolean edit(@RequestBody ScoreVO scoreVO) {
//        throw new PageTipException("考核已结束,无法批量评分");
        return scoreService.updateBatchById(scoreVO.getScoreList());
    }

    @GetMapping("getScoreList")
    public List<Score> getScoreList() {
        List<Score> list;
        CheckUser user = (CheckUser) httpSession.getAttribute("user");
        if (user == null) {
            throw new PageTipException("用户未登录");
        }
        list = scoreService.list(new LambdaQueryWrapper<Score>().eq(Score::getUserName, user.getName()));
        List<Upload> uploadList = uploadService.list(new LambdaQueryWrapper<Upload>().eq(Upload::getYear, 2021).in(Upload::getUserName, list.stream().map(Score::getUserrName).collect(Collectors.toList())));
        Map<String, String> uploadMap = new HashMap<>();
        for (Upload upload : uploadList) {
            uploadMap.put(upload.getUserName(), upload.getDiskName());
        }
        for (Score score : list) {
            score.setDiskName(uploadMap.get(score.getUserrName()));
        }
        return list;
    }

    //单人评分
    @PostMapping("edit2")
    public boolean edit2(@RequestBody Score score) {
//        throw new PageTipException("考核已结束,无法评分");
        return scoreService.updateById(score);
    }

    @GetMapping("getScoreList2")
    public List<Score> getScoreList2(Integer id, String userrType, String checkkObject) {
        CheckUser user = (CheckUser) httpSession.getAttribute("user");
        if (user == null) {
            throw new PageTipException("用户未登录");
        }
        LambdaQueryWrapper<Score> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Score::getUserName, user.getName()).eq(Score::getScoreType, "行政评分").ne(Score::getId, id);
        if (userrType.equals("一般人员")) {
            wrapper.eq(Score::getUserrType, userrType);
        } else if (checkkObject.equals("副总师级") || checkkObject.equals("部门正职领导")) {
            wrapper.in(Score::getCheckkObject, Arrays.asList("副总师级", "部门正职领导"));
        } else if (checkkObject.equals("部门副职领导")) {
            wrapper.eq(Score::getCheckkObject, checkkObject);
        }
        return scoreService.list(wrapper);
    }

    @GetMapping("getCheckkObject")
    public List<LabelValue> getCheckkObject() {
        CheckUser user = (CheckUser) httpSession.getAttribute("user");
        if (user == null) {
            throw new PageTipException("用户未登录");
        }
        QueryWrapper<Score> wrapper = new QueryWrapper<Score>().select("distinct checkk_object");
        List<Score> list = scoreService.list(wrapper);
        return list.stream().map(item -> new LabelValue(item.getCheckkObject(), item.getCheckkObject())).collect(Collectors.toList());
    }

    @GetMapping("getCheckUserType")
    public List<LabelValue> getCheckUserType() {
        QueryWrapper<Score> wrapper = new QueryWrapper<Score>().select("distinct check_user_type");
        List<Score> list = scoreService.list(wrapper);
        return list.stream().map(item -> new LabelValue(item.getCheckUserType(), item.getCheckUserType())).collect(Collectors.toList());
    }

    @GetMapping("getCheckkObjectAll")
    public List<LabelValue> getCheckkObjectAll() {
        QueryWrapper<Score> wrapper = new QueryWrapper<Score>().select("distinct checkk_object");
        List<Score> list = scoreService.list(wrapper);
        return list.stream().map(item -> new LabelValue(item.getCheckkObject(), item.getCheckkObject())).collect(Collectors.toList());
    }

    //下载评分模板
    @GetMapping("download1")
    public void downloadTemplate(HttpServletResponse response) throws IOException {
        CheckUser user = (CheckUser) httpSession.getAttribute("user");
        if (user == null) {
            throw new PageTipException("用户未登录");
        }
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode(user.getName() + "的评分模板", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        //
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).useDefaultStyle(false).excelType(ExcelTypeEnum.XLS).build();
        //
        List<ScoreExcel> data0List = new ArrayList<>();
        List<Score> scoreList = scoreService.list(new LambdaQueryWrapper<Score>().eq(Score::getUserName, user.getName()));
        for (Score score : scoreList) {
            ScoreExcel scoreExcel = new ScoreExcel();
            BeanUtils.copyProperties(score, scoreExcel);
            data0List.add(scoreExcel);
        }
        WriteSheet sheet0 = EasyExcel.writerSheet(0, "评分信息").head(ScoreExcel.class).build();
        //
        excelWriter.write(data0List, sheet0);
        //
        excelWriter.finish();
    }

    //下载评分
    @GetMapping("download2")
    public void download2(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("2022年全员得分情况", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        //
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).useDefaultStyle(false).excelType(ExcelTypeEnum.XLS).build();
        //
        List<ScoreResult22Excel> data0List = new ArrayList<>();
        List<ScoreResult22Excel> data1List = new ArrayList<>();
        List<ScoreResult22> scoreList0 = scoreResult22Service.list(new QueryWrapper<ScoreResult22>().eq("score_type", "行政评分").orderByAsc("deptt_sort"));
        List<ScoreResult22> scoreList1 = scoreResult22Service.list(new QueryWrapper<ScoreResult22>().eq("score_type", "党务评分"));
        for (ScoreResult22 score : scoreList0) {
            ScoreResult22Excel scoreExcel = new ScoreResult22Excel();
            BeanUtils.copyProperties(score, scoreExcel);
            data0List.add(scoreExcel);
        }
        for (ScoreResult22 score : scoreList1) {
            ScoreResult22Excel scoreExcel = new ScoreResult22Excel();
            BeanUtils.copyProperties(score, scoreExcel);
            data1List.add(scoreExcel);
        }
        WriteSheet sheet0 = EasyExcel.writerSheet(0, "行政评分").head(ScoreResult22Excel.class).build();
        WriteSheet sheet1 = EasyExcel.writerSheet(1, "党务评分").head(ScoreResult22Excel.class).build();
        //
        excelWriter.write(data0List, sheet0);
        excelWriter.write(data1List, sheet1);
        //
        excelWriter.finish();
    }

    //计算-score_result1
    @GetMapping("computeScoreResult1")
    public boolean computeScoreResult1() {
        return scoreService.computeScoreResult1(2021);
    }

    //计算-score_result11
    @GetMapping("computeScoreResult11")
    public boolean computeScoreResult11() {
        return scoreService.computeScoreResult11(2021);
    }


    @GetMapping("computeScoreResult22")
    public boolean computeScoreResult22() {
        return scoreService.computeScoreResult22(2021);
    }

    @GetMapping("computeScoreResult23")
    public boolean computeScoreResult23() {
        return scoreService.computeScoreResult23(2021);
    }
}
