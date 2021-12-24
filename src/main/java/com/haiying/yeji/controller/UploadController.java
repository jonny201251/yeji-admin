package com.haiying.yeji.controller;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haiying.yeji.common.result.ResponseResultWrapper;
import com.haiying.yeji.model.entity.CheckUser;
import com.haiying.yeji.model.entity.Upload;
import com.haiying.yeji.model.vo.FileVO;
import com.haiying.yeji.model.vo.UploadVO;
import com.haiying.yeji.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 述职材料 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2021-12-24
 */
@RestController
@RequestMapping("/upload")
@ResponseResultWrapper
public class UploadController {
    @Autowired
    UploadService uploadService;
    @Autowired
    HttpSession httpSession;

    @GetMapping("list")
    public IPage<Upload> list(int current, int pageSize, String name) {
        CheckUser user = (CheckUser) httpSession.getAttribute("user");
        QueryWrapper<Upload> wrapper = new QueryWrapper<Upload>().select("distinct year,name").eq("user_name", user.getName()).orderByDesc("year");
        if (ObjectUtil.isNotEmpty(name)) {
            wrapper.like("name", name);
        }
        return uploadService.page(new Page<>(current, pageSize), wrapper);
    }


    @PostMapping("add")
    public boolean add(@RequestBody UploadVO uploadVO) {
        CheckUser user = (CheckUser) httpSession.getAttribute("user");
        List<Upload> list = new ArrayList<>();
        for (FileVO fileVO : uploadVO.getUploadList()) {
            Upload upload = new Upload();
            upload.setYear(uploadVO.getYear());
            upload.setName(uploadVO.getName());
            upload.setUserName(user.getName());
            upload.setFileName(fileVO.getName());
            upload.setDiskName(fileVO.getUrl());
            list.add(upload);
        }
        return uploadService.saveBatch(list);
    }

    @GetMapping("get")
    public UploadVO get(Integer year) {
        UploadVO uploadVO = new UploadVO();
        List<FileVO> uploadList = new ArrayList<>();

        CheckUser user = (CheckUser) httpSession.getAttribute("user");
        List<Upload> list = uploadService.list(new LambdaQueryWrapper<Upload>().eq(Upload::getYear, year).eq(Upload::getUserName, user.getName()));
        for (Upload upload : list) {
            FileVO fileVO = new FileVO();
            fileVO.setStatus("done");
            fileVO.setName(upload.getFileName());
            fileVO.setUrl(upload.getDiskName());
            uploadList.add(fileVO);
        }
        uploadVO.setName(list.get(0).getName());
        uploadVO.setYear(year);
        uploadVO.setUploadList(uploadList);
        return uploadVO;
    }

    @PostMapping("edit")
    public boolean edit(@RequestBody UploadVO uploadVO) {
        //先删除db
        CheckUser user = (CheckUser) httpSession.getAttribute("user");
        uploadService.remove(new LambdaQueryWrapper<Upload>().eq(Upload::getYear, uploadVO.getYear()).eq(Upload::getUserName, user.getName()));
        return add(uploadVO);
    }

    @GetMapping("delete")
    public boolean delete(Integer[] arr) {
        List<Integer> list = Stream.of(arr).collect(Collectors.toList());
        CheckUser user = (CheckUser) httpSession.getAttribute("user");
        return uploadService.remove(new LambdaQueryWrapper<Upload>().in(Upload::getYear, list).eq(Upload::getUserName, user.getName()));
    }

}
