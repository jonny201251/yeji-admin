package com.haiying.yeji.controller;


import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haiying.yeji.common.result.ResponseResult;
import com.haiying.yeji.common.result.ResponseResultWrapper;
import com.haiying.yeji.common.utils.MyPageUtil;
import com.haiying.yeji.model.entity.CheckUser;
import com.haiying.yeji.model.entity.CheckkObject;
import com.haiying.yeji.model.entity.Score;
import com.haiying.yeji.model.entity.ScoreResult22;
import com.haiying.yeji.model.vo.CheckkObjectScoreTmp;
import com.haiying.yeji.model.vo.SearchScoreVO;
import com.haiying.yeji.service.CheckkObjectService;
import com.haiying.yeji.service.ScoreResult22Service;
import com.haiying.yeji.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
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
@RequestMapping("/searchScore")
@ResponseResultWrapper
public class SearchScoreController {
    @Autowired
    ScoreService scoreService;
    @Autowired
    HttpSession httpSession;
    @Autowired
    CheckkObjectService checkkObjectService;
    @Autowired
    ScoreResult22Service scoreResult22Service;

    @GetMapping("list")
    public IPage<ScoreResult22> list(int current, int pageSize, String depttName) {
        CheckUser checkUser = (CheckUser) httpSession.getAttribute("user");
        LambdaQueryWrapper<ScoreResult22> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ScoreResult22::getDepttName, checkUser.getDeptName());

        return scoreResult22Service.page(new Page<>(current, pageSize), wrapper);
    }

    @GetMapping("list2")
    public ResponseResult list2(int current, int pageSize, Integer year) {
        CheckUser checkUser = (CheckUser) httpSession.getAttribute("user");
        Integer checkYear = (Integer) httpSession.getAttribute("checkYear");
        //评分启动状态
        LambdaQueryWrapper<Score> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Score::getScoreType, "行政评分")
                .eq(Score::getYear, 2021)
                .eq(Score::getStatus, "已评分")
                .eq(Score::getDeptId, checkUser.getDeptId())
                .eq(Score::getUserrType, "一般人员");
        List<Score> scoreList = scoreService.list(wrapper);
        //评分结果2
        List<SearchScoreVO> dataList2 = new ArrayList<>();
        //
        List<CheckkObject> checkkObjectList = checkkObjectService.list();
        Map<String, List<CheckkObject>> checkkObjectMap = new HashMap<>();
        for (CheckkObject checkkObject : checkkObjectList) {
            String str = checkkObject.getCheckkObject();
            if (checkkObjectMap.get(str) == null) {
                List<CheckkObject> list = new ArrayList<>();
                list.add(checkkObject);
                checkkObjectMap.put(str, list);
            } else {
                checkkObjectMap.get(str).add(checkkObject);
            }
        }
        //
        List<String> userrNameList = new ArrayList<>();
        Map<String, Score> userrNameMap = new TreeMap<>();
        for (Score score : scoreList) {
            if (userrNameMap.get(score.getUserrName()) == null) {
                userrNameList.add(score.getUserrName());
                userrNameMap.put(score.getUserrName(), score);
            }
        }
        //
        for (String userrName : userrNameList) {
            //评分结果1
            List<CheckkObjectScoreTmp> dataList1 = new ArrayList<>();
            Score score = userrNameMap.get(userrName);
            List<CheckkObject> objectList = checkkObjectMap.get(score.getCheckkObject());
            for (CheckkObject object : objectList) {
                CheckkObjectScoreTmp tmp = new CheckkObjectScoreTmp();
                tmp.setCheckUserType(object.getCheckUserType());
                tmp.setScore0(0d);
                tmp.setScore1(0d);
                tmp.setScore2(0d);
                tmp.setScore3(0d);
                tmp.setScore4(0d);
                tmp.setScore5(0d);
                tmp.setScore6(0d);
                tmp.setCheckWeight(object.getWeight() * 0.01);
                List<Score> list = scoreList.stream().filter(item -> item.getUserrName().equals(userrName) && item.getCheckUserType().equals(object.getCheckUserType())).collect(Collectors.toList());
                //
                for (Score scoree : list) {
                    tmp.setScore0(tmp.getScore0() + scoree.getScore0());
                    tmp.setScore1(tmp.getScore1() + scoree.getScore1());
                    tmp.setScore2(tmp.getScore2() + scoree.getScore2());
                    tmp.setScore3(tmp.getScore3() + scoree.getScore3());
                    tmp.setScore4(tmp.getScore4() + scoree.getScore4());
                    tmp.setScore5(tmp.getScore5() + scoree.getScore5());
                    tmp.setScore6(tmp.getScore6() + scoree.getScore6());
                }
                if (list.size() == 0) {
                    continue;
                }
                //
                tmp.setScore0(NumberUtil.div(tmp.getScore0(), Double.valueOf("" + list.size()), 2));
                tmp.setScore1(NumberUtil.div(tmp.getScore1(), Double.valueOf("" + list.size()), 2));
                tmp.setScore2(NumberUtil.div(tmp.getScore2(), Double.valueOf("" + list.size()), 2));
                tmp.setScore3(NumberUtil.div(tmp.getScore3(), Double.valueOf("" + list.size()), 2));
                tmp.setScore4(NumberUtil.div(tmp.getScore4(), Double.valueOf("" + list.size()), 2));
                tmp.setScore5(NumberUtil.div(tmp.getScore5(), Double.valueOf("" + list.size()), 2));
                tmp.setScore6(NumberUtil.div(tmp.getScore6(), Double.valueOf("" + list.size()), 2));
                dataList1.add(tmp);
            }
            //评分结果2
            SearchScoreVO searchScoreVO = new SearchScoreVO();
            searchScoreVO.setYear(2021);
            searchScoreVO.setDepttName(score.getDepttName());
            searchScoreVO.setCheckkObject(score.getCheckkObject());
            searchScoreVO.setUserrName(score.getUserrName());
            searchScoreVO.setScore0(0d);
            searchScoreVO.setScore1(0d);
            searchScoreVO.setScore2(0d);
            searchScoreVO.setScore3(0d);
            searchScoreVO.setScore4(0d);
            searchScoreVO.setScore5(0d);
            searchScoreVO.setScore6(0d);
            for (CheckkObjectScoreTmp checkkObjectScoreTmp : dataList1) {
                searchScoreVO.setScore0(NumberUtil.round(searchScoreVO.getScore0() + checkkObjectScoreTmp.getScore0() * checkkObjectScoreTmp.getCheckWeight(), 2).doubleValue());
                searchScoreVO.setScore1(NumberUtil.round(searchScoreVO.getScore1() + checkkObjectScoreTmp.getScore1() * checkkObjectScoreTmp.getCheckWeight(), 2).doubleValue());
                searchScoreVO.setScore2(NumberUtil.round(searchScoreVO.getScore2() + checkkObjectScoreTmp.getScore2() * checkkObjectScoreTmp.getCheckWeight(), 2).doubleValue());
                searchScoreVO.setScore3(NumberUtil.round(searchScoreVO.getScore3() + checkkObjectScoreTmp.getScore3() * checkkObjectScoreTmp.getCheckWeight(), 2).doubleValue());
                searchScoreVO.setScore4(NumberUtil.round(searchScoreVO.getScore4() + checkkObjectScoreTmp.getScore4() * checkkObjectScoreTmp.getCheckWeight(), 2).doubleValue());
                searchScoreVO.setScore5(NumberUtil.round(searchScoreVO.getScore5() + checkkObjectScoreTmp.getScore5() * checkkObjectScoreTmp.getCheckWeight(), 2).doubleValue());
                searchScoreVO.setScore6(NumberUtil.round(searchScoreVO.getScore6() + checkkObjectScoreTmp.getScore6() * checkkObjectScoreTmp.getCheckWeight(), 2).doubleValue());
            }
            //
            Double totalScore = searchScoreVO.getScore0() * 0.1d +
                    searchScoreVO.getScore1() * 0.1d +
                    searchScoreVO.getScore2() * 0.1d +
                    searchScoreVO.getScore3() * 0d +
                    searchScoreVO.getScore4() * 0.15d +
                    searchScoreVO.getScore5() * 0.15d +
                    searchScoreVO.getScore6() * 0.4d;
            searchScoreVO.setTotalScore(NumberUtil.round(totalScore, 2).doubleValue());
            dataList2.add(searchScoreVO);
        }
        return MyPageUtil.get(current, pageSize, dataList2.size(), dataList2);
    }
}
