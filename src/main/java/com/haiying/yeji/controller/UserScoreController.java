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
import com.haiying.yeji.model.entity.Upload;
import com.haiying.yeji.model.excel.ScoreExcel;
import com.haiying.yeji.model.vo.LabelValue;
import com.haiying.yeji.model.vo.ScoreVO;
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
        List<Upload> uploadList = uploadService.list(new LambdaQueryWrapper<Upload>().eq(Upload::getYear, 2021).in(Upload::getUserName, list.stream().map(Score::getUserrName).collect(Collectors.toList())));
        Map<String, String> uploadMap = new HashMap<>();
        for (Upload upload : uploadList) {
            uploadMap.put(upload.getUserName(),upload.getDiskName());
        }
        for (Score score : list) {
            score.setDiskName(uploadMap.get(score.getUserrName()));
        }
        return page;
    }

    //批量评分
    @PostMapping("edit")
    public boolean edit(@RequestBody ScoreVO scoreVO) {
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
            uploadMap.put(upload.getUserName(),upload.getDiskName());
        }
        for (Score score : list) {
            score.setDiskName(uploadMap.get(score.getUserrName()));
        }
        return list;
    }

    //单人评分
    @PostMapping("edit2")
    public boolean edit2(@RequestBody Score score) {
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
        QueryWrapper<Score> wrapper = new QueryWrapper<Score>().eq("user_name", user.getName()).select("distinct checkk_object");
        List<Score> list = scoreService.list(wrapper);
        return list.stream().map(item -> new LabelValue(item.getCheckkObject(), item.getCheckkObject())).collect(Collectors.toList());
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
        String fileName = URLEncoder.encode(user.getName()+"的评分模板", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        //
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).useDefaultStyle(false).excelType(ExcelTypeEnum.XLS).build();
        //
        List<ScoreExcel> data0List = new ArrayList<>();
        List<Score> scoreList = scoreService.list(new LambdaQueryWrapper<Score>().eq(Score::getUserName, user.getName()));
        for (Score score : scoreList) {
            ScoreExcel scoreExcel=new ScoreExcel();
            BeanUtils.copyProperties(score,scoreExcel);
            data0List.add(scoreExcel);
        }
        WriteSheet sheet0 = EasyExcel.writerSheet(0, "评分信息").head(ScoreExcel.class).build();
        //
        excelWriter.write(data0List, sheet0);
        //
        excelWriter.finish();
    }
}
