package com.haiying.yeji.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haiying.yeji.common.exception.PageTipException;
import com.haiying.yeji.mapper.ScoreMapper;
import com.haiying.yeji.model.entity.*;
import com.haiying.yeji.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 评分 服务实现类
 * </p>
 *
 * @author 作者
 * @since 2021-12-12
 */
@Service
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, Score> implements ScoreService {
    @Autowired
    CheckkObjectService checkkObjectService;
    @Autowired
    CheckUserService checkUserService;
    @Autowired
    ChargeDeptLeaderService chargeDeptLeaderService;
    @Autowired
    DeptScoreRelationService deptScoreRelationService;
    @Autowired
    PartySecretaryService partySecretaryService;
    @Autowired
    PartyService partyService;

    private void partyAdd(Integer year, CheckkObject checkkObject, CheckUser checkkUser, List<CheckUser> checkUserList) {
        if (ObjectUtil.isNotEmpty(checkUserList)) {
            List<Score> scoreList = new ArrayList<>();
            for (CheckUser checkUser : checkUserList) {
                Score score = new Score();
                score.setYear(year);
                score.setScoreType("党务评分");
                score.setPartyName(checkkUser.getPartyName());
                //
                score.setDepttId(checkkUser.getDeptId());
                score.setDepttName(checkkUser.getDeptName());
                score.setDepttSort(checkkUser.getDeptSort());
                score.setUserrName(checkkUser.getName());
                score.setUserrType("  ");
                score.setUserrRole(checkkUser.getUserRole());
                score.setUserrSort(checkkUser.getUserSort());
                score.setCheckkObject(checkkObject.getCheckkObject());
                score.setCheckkObjectSort(checkkObject.getCheckkObjectSort());
                //
                score.setDeptId(checkUser.getDeptId());
                score.setDeptName(checkUser.getDeptName());
                score.setDeptSort(checkUser.getDeptSort());
                score.setUserName(checkUser.getName());
                score.setUserType(checkUser.getUserType());
                score.setUserRole(checkUser.getUserRole());
                score.setCheckUserType(checkkObject.getCheckUserType());
                score.setCheckUserTypeSort(checkkObject.getCheckUserTypeSort());
                score.setWeight(checkkObject.getWeight());
                //
                score.setStatus("未评分");
                scoreList.add(score);
            }
            this.saveBatch(scoreList);
        }
    }

    private void deptAdd(Integer year, CheckkObject checkkObject, CheckUser checkkUser, List<CheckUser> checkUserList) {
        if (ObjectUtil.isNotEmpty(checkUserList)) {
            List<Score> scoreList = new ArrayList<>();
            for (CheckUser checkUser : checkUserList) {
                Score score = new Score();
                score.setYear(year);
                score.setScoreType("行政评分");
                //
                score.setDepttId(checkkUser.getDeptId());
                score.setDepttName(checkkUser.getDeptName());
                score.setDepttSort(checkkUser.getDeptSort());
                score.setUserrName(checkkUser.getName());
                score.setUserrType(checkkUser.getUserType());
                score.setUserrRole(checkkUser.getUserRole());
                score.setUserrSort(checkkUser.getUserSort());
                if (checkkObject.getCheckkObject().equals("安全生产总监") || checkkObject.getCheckkObject().equals("财务副总监")) {
                    score.setCheckkObject("副总师级");
                } else {
                    score.setCheckkObject(checkkObject.getCheckkObject());
                }
                score.setCheckkObjectSort(checkkObject.getCheckkObjectSort());
                //
                if (checkkUser.getUserType().equals("一般人员")) {
                    score.setScore3(0d);
                }
                //
                score.setDeptId(checkUser.getDeptId());
                score.setDeptName(checkUser.getDeptName());
                score.setDeptSort(checkUser.getDeptSort());
                score.setUserName(checkUser.getName());
                score.setUserType(checkUser.getUserType());
                score.setUserRole(checkUser.getUserRole());
                score.setCheckUserType(checkkObject.getCheckUserType());
                score.setCheckUserTypeSort(checkkObject.getCheckUserTypeSort());
                score.setWeight(checkkObject.getWeight());
                //
                score.setStatus("未评分");
                scoreList.add(score);
            }
            this.saveBatch(scoreList);
        }
    }

    private List<CheckUser> get1List(List<CheckUser> checkUserList, List<ChargeDeptLeader> chargeDeptLeaderList, CheckUser checkkUser) {
        Set<Integer> deptIdSet = chargeDeptLeaderList.stream().filter(item -> item.getUserName().equals(checkkUser.getName())).map(ChargeDeptLeader::getDeptId).collect(Collectors.toSet());
        return checkUserList
                .stream()
                .filter(item -> item.getUserRole().equals("部门正职领导") || item.getUserRole().equals("部门副职领导"))
                .filter(item -> deptIdSet.contains(item.getDeptId()))
                .filter(item -> !item.getName().equals(checkkUser.getName()))
                .collect(Collectors.toList());
    }

    private List<CheckUser> get2List(List<CheckUser> checkUserList, List<ChargeDeptLeader> chargeDeptLeaderList, CheckUser checkkUser) {
        List<ChargeDeptLeader> list = chargeDeptLeaderList.stream().filter(item -> item.getDeptId().equals(checkkUser.getDeptId())).collect(Collectors.toList());
        Set<String> userNameSet = list.stream().map(ChargeDeptLeader::getUserName).collect(Collectors.toSet());
        return checkUserList.stream()
                .filter(item -> userNameSet.contains(item.getName()))
                .filter(item -> !item.getName().equals(checkkUser.getName()))
                .collect(Collectors.toList());
    }

    private List<CheckUser> get3List(List<CheckUser> checkUserList, List<ChargeDeptLeader> chargeDeptLeaderList, CheckUser checkkUser) {
        //先取出主管部门领导
        List<ChargeDeptLeader> list = chargeDeptLeaderList.stream().filter(item -> item.getDeptId().equals(checkkUser.getDeptId())).collect(Collectors.toList());
        Set<String> userNameSet = list.stream().map(ChargeDeptLeader::getUserName).collect(Collectors.toSet());
        //用户类型=公司领导
        List<CheckUser> list2 = checkUserList.stream().filter(item -> item.getUserType().equals("公司领导")).collect(Collectors.toList());
        return list2.stream()
                .filter(item -> !userNameSet.contains(item.getName()))
                .filter(item -> !item.getName().equals(checkkUser.getName()))
                .collect(Collectors.toList());
    }

    private List<CheckUser> get4List(List<CheckUser> checkUserList, List<DeptScoreRelation> deptScoreRelationList, CheckUser checkkUser) {
        Set<Integer> deptIdSet = deptScoreRelationList.stream()
                .filter(item -> item.getScoreeDeptId().equals(checkkUser.getDeptId()))
                .map(DeptScoreRelation::getScoreDeptId)
                .collect(Collectors.toSet());
        return checkUserList
                .stream()
                .filter(item -> item.getUserRole().equals("部门正职领导") || item.getUserRole().equals("部门副职领导"))
                .filter(item -> deptIdSet.contains(item.getDeptId()))
                .filter(item -> !item.getName().equals(checkkUser.getName()))
                .collect(Collectors.toList());
    }

    private List<CheckUser> get5List(List<CheckUser> checkUserList, CheckUser checkkUser) {
        return checkUserList
                .stream()
                .filter(item -> item.getDeptId().equals(checkkUser.getDeptId()))
                .filter(item -> !item.getUserRole().equals("部门正职领导") && !item.getUserRole().equals("特别人员"))
                .filter(item -> !item.getName().equals(checkkUser.getName()))
                .collect(Collectors.toList());
    }

    private List<CheckUser> get6List(List<CheckUser> checkUserList, CheckUser checkkUser) {
        return checkUserList
                .stream()
                .filter(item -> item.getDeptId().equals(checkkUser.getDeptId()))
                .filter(item -> item.getUserRole().equals("部门正职领导") && !item.getUserRole().equals("特别人员"))
                .filter(item -> !item.getName().equals(checkkUser.getName()))
                .collect(Collectors.toList());
    }

    private List<CheckUser> get7List(List<CheckUser> checkUserList, CheckUser checkkUser) {
        return checkUserList
                .stream()
                .filter(item -> item.getDeptId().equals(checkkUser.getDeptId()))
                .filter(item -> item.getUserRole().equals("部门正职领导") || item.getUserRole().equals("部门副职领导"))
                .filter(item -> !item.getName().equals(checkkUser.getName()))
                .collect(Collectors.toList());
    }

    private List<CheckUser> get8List(List<CheckUser> checkUserList, CheckUser checkkUser) {
        return checkUserList
                .stream()
                .filter(item -> item.getDeptId().equals(checkkUser.getDeptId()))
                .filter(item -> item.getUserRole().equals("一般管理人员"))
                .filter(item -> !item.getName().equals(checkkUser.getName()))
                .collect(Collectors.toList());
    }

    private List<CheckUser> get9List(List<CheckUser> checkUserList, CheckUser checkkUser) {
        return checkUserList
                .stream()
                .filter(item -> item.getDeptId().equals(checkkUser.getDeptId()))
                .filter(item -> item.getGroupId() != null && item.getGroupId().equals(checkkUser.getGroupId()))
                .filter(item -> item.getUserRole().equals("班组成员"))
                .filter(item -> !item.getName().equals(checkkUser.getName()))
                .collect(Collectors.toList());
    }

    private List<CheckUser> get10List(List<CheckUser> checkUserList, CheckUser checkkUser) {
        return checkUserList
                .stream()
                .filter(item -> item.getDeptId().equals(checkkUser.getDeptId()))
                .filter(item -> item.getGroupId() != null && item.getGroupId().equals(checkkUser.getGroupId()))
                .filter(item -> item.getUserRole().equals("班组长"))
                .filter(item -> !item.getName().equals(checkkUser.getName()))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public boolean generate(Integer year) {
        //先删除,后插入
        this.remove(new LambdaQueryWrapper<Score>().eq(Score::getYear, year));
        //
        List<CheckkObject> checkkObjectList = checkkObjectService.list(new LambdaQueryWrapper<CheckkObject>().orderByAsc(Arrays.asList(CheckkObject::getCheckkObjectSort, CheckkObject::getCheckUserTypeSort)));
        List<CheckUser> checkUserList = checkUserService.list(new LambdaQueryWrapper<CheckUser>().eq(CheckUser::getWorkStatus, "在岗").orderByAsc(Arrays.asList(CheckUser::getDeptSort, CheckUser::getUserSort)));
        List<ChargeDeptLeader> chargeDeptLeaderList = chargeDeptLeaderService.list(new LambdaQueryWrapper<ChargeDeptLeader>().orderByAsc(Arrays.asList(ChargeDeptLeader::getUserSort)));
        List<PartySecretary> partySecretaryList = partySecretaryService.list();
        List<Party> partyList = partyService.list();
        List<DeptScoreRelation> deptScoreRelationList = deptScoreRelationService.list();
        //
        for (CheckkObject obj : checkkObjectList) {
            //考核人员
            List<CheckUser> list;
            //被考核对象
            if (obj.getCheckkObject().equals("安全生产总监")) {
                //遍历出具体人
                List<CheckUser> list2 = checkUserList.stream().filter(item -> item.getUserRole().equals(obj.getCheckkObject())).collect(Collectors.toList());
                for (CheckUser checkkUser : list2) {
                    //考核人员类型
                    if (obj.getCheckUserType().equals("公司领导")) {
                        //用户角色=公司领导
                        list = checkUserList.stream().filter(item -> item.getUserRole().equals("公司领导")).collect(Collectors.toList());
                    } else if (obj.getCheckUserType().equals("主管部门正副职")) {
                        list = get1List(checkUserList, chargeDeptLeaderList, checkkUser);
                    } else {
                        throw new PageTipException(obj.getCheckUserType() + "--错误");
                    }
                    deptAdd(year, obj, checkkUser, list);
                }
            } else if (obj.getCheckkObject().equals("副总师级") || obj.getCheckkObject().equals("财务副总监")) {
                List<CheckUser> list2 = checkUserList.stream().filter(item -> item.getUserRole().equals(obj.getCheckkObject())).collect(Collectors.toList());
                for (CheckUser checkkUser : list2) {
                    if (obj.getCheckUserType().equals("公司领导")) {
                        //用户类型=公司领导
                        list = checkUserList.stream().filter(item -> item.getUserType().equals("公司领导")).collect(Collectors.toList());
                    } else if (obj.getCheckUserType().equals("主管部门正副职")) {
                        list = get1List(checkUserList, chargeDeptLeaderList, checkkUser);
                    } else {
                        throw new PageTipException(obj.getCheckUserType() + "--错误");
                    }
                    deptAdd(year, obj, checkkUser, list);
                }
            } else if (obj.getCheckkObject().equals("部门正职领导")) {
                List<CheckUser> list2 = checkUserList.stream().filter(item -> item.getUserRole().equals(obj.getCheckkObject())).collect(Collectors.toList());
                for (CheckUser checkkUser : list2) {
                    if (obj.getCheckUserType().equals("主管公司领导")) {
                        list = get2List(checkUserList, chargeDeptLeaderList, checkkUser);
                    } else if (obj.getCheckUserType().equals("其他公司领导")) {
                        list = get3List(checkUserList, chargeDeptLeaderList, checkkUser);
                    } else if (obj.getCheckUserType().equals("相关部门正副职领导")) {
                        list = get4List(checkUserList, deptScoreRelationList, checkkUser);
                    } else if (obj.getCheckUserType().equals("部门人员")) {
                        list = get5List(checkUserList, checkkUser);
                    } else {
                        throw new PageTipException(obj.getCheckUserType() + "--错误");
                    }
                    deptAdd(year, obj, checkkUser, list);
                }
            } else if (obj.getCheckkObject().equals("部门副职领导")) {
                List<CheckUser> list2 = checkUserList.stream().filter(item -> item.getUserRole().equals(obj.getCheckkObject())).collect(Collectors.toList());
                for (CheckUser checkkUser : list2) {
                    if (obj.getCheckUserType().equals("主管公司领导")) {
                        list = get2List(checkUserList, chargeDeptLeaderList, checkkUser);
                    } else if (obj.getCheckUserType().equals("其他公司领导")) {
                        list = get3List(checkUserList, chargeDeptLeaderList, checkkUser);
                    } else if (obj.getCheckUserType().equals("相关部门正副职领导")) {
                        list = get4List(checkUserList, deptScoreRelationList, checkkUser);
                    } else if (obj.getCheckUserType().equals("本部门正职领导")) {
                        list = get6List(checkUserList, checkkUser);
                    } else if (obj.getCheckUserType().equals("部门人员")) {
                        list = get5List(checkUserList, checkkUser);
                    } else {
                        throw new PageTipException(obj.getCheckUserType() + "--错误");
                    }
                    deptAdd(year, obj, checkkUser, list);
                }
            } else if (obj.getCheckkObject().equals("一般管理人员")) {
                List<CheckUser> list2 = checkUserList.stream().filter(item -> item.getUserRole().equals(obj.getCheckkObject())).collect(Collectors.toList());
                for (CheckUser checkkUser : list2) {
                    if (obj.getCheckUserType().equals("本部门正副职领导")) {
                        list = get7List(checkUserList, checkkUser);
                    } else if (obj.getCheckUserType().equals("一般管理人员")) {
                        list = get8List(checkUserList, checkkUser);
                    } else {
                        throw new PageTipException(obj.getCheckUserType() + "--错误");
                    }
                    deptAdd(year, obj, checkkUser, list);
                }
            } else if (obj.getCheckkObject().equals("班组长")) {
                List<CheckUser> list2 = checkUserList.stream().filter(item -> item.getUserRole().equals(obj.getCheckkObject())).collect(Collectors.toList());
                for (CheckUser checkkUser : list2) {
                    if (obj.getCheckUserType().equals("本部门正副职领导")) {
                        list = get7List(checkUserList, checkkUser);
                    } else if (obj.getCheckUserType().equals("班组成员")) {
                        list = get9List(checkUserList, checkkUser);
                    } else {
                        throw new PageTipException(obj.getCheckUserType() + "--错误");
                    }
                    deptAdd(year, obj, checkkUser, list);
                }
            } else if (obj.getCheckkObject().equals("班组成员")) {
                List<CheckUser> list2 = checkUserList.stream().filter(item -> item.getUserRole().equals(obj.getCheckkObject())).collect(Collectors.toList());
                for (CheckUser checkkUser : list2) {
                    if (obj.getCheckUserType().equals("本部门正副职领导")) {
                        list = get7List(checkUserList, checkkUser);
                    } else if (obj.getCheckUserType().equals("班组长")) {
                        list = get10List(checkUserList, checkkUser);
                    } else if (obj.getCheckUserType().equals("班组成员")) {
                        list = get9List(checkUserList, checkkUser);
                    } else {
                        throw new PageTipException(obj.getCheckUserType() + "--错误");
                    }
                    deptAdd(year, obj, checkkUser, list);
                }
            } else if (obj.getCheckkObject().equals("特别人员")) {
                List<CheckUser> list2 = checkUserList.stream().filter(item -> item.getUserRole().equals(obj.getCheckkObject())).collect(Collectors.toList());
                for (CheckUser checkkUser : list2) {
                    if (obj.getCheckUserType().equals("本部门正副职领导")) {
                        list = get7List(checkUserList, checkkUser);
                    } else {
                        throw new PageTipException(obj.getCheckUserType() + "--错误");
                    }
                    deptAdd(year, obj, checkkUser, list);
                }
            } else if (obj.getCheckkObject().equals("党支部书记")) {
                List<CheckUser> list2 = checkUserList.stream()
                        .filter(item -> item.getPartyRole() != null)
                        .filter(item -> item.getPartyRole().equals("专职支部书记") || item.getPartyRole().equals("兼职支部书记"))
                        .collect(Collectors.toList());
                for (CheckUser checkkUser : list2) {
                    //考核人员类型
                    if (obj.getCheckUserType().equals("党委书记")) {
                        Set<String> nameSet = partySecretaryList.stream().filter(item -> item.getType().equals("党委书记")).map(PartySecretary::getUserName).collect(Collectors.toSet());
                        list = checkUserList.stream().filter(item -> nameSet.contains(item.getName())).collect(Collectors.toList());
                    } else if (obj.getCheckUserType().equals("党委副书记")) {
                        Set<String> nameSet = partySecretaryList.stream().filter(item -> item.getType().equals("党委副书记")).map(PartySecretary::getUserName).collect(Collectors.toSet());
                        list = checkUserList.stream().filter(item -> nameSet.contains(item.getName())).collect(Collectors.toList());
                    } else if (obj.getCheckUserType().equals("公司领导")) {
                        //排除-党委书记、党委副书记
                        Set<String> excludeNameSet = partySecretaryList.stream().map(PartySecretary::getUserName).collect(Collectors.toSet());
                        list = checkUserList.stream()
                                .filter(item -> item.getUserType().equals("公司领导"))
                                .filter(item -> !excludeNameSet.contains(item.getName()))
                                .collect(Collectors.toList());
                    } else if (obj.getCheckUserType().equals("党支部书记")) {
                        list = checkUserList.stream()
                                .filter(item -> item.getPartyRole() != null)
                                .filter(item -> item.getPartyRole().equals("专职支部书记") || item.getPartyRole().equals("兼职支部书记"))
                                .filter(item -> !checkkUser.getName().contains(item.getName()))
                                .collect(Collectors.toList());
                    } else if (obj.getCheckUserType().equals("党群工作部正职领导")) {
                        list = checkUserList.stream()
                                .filter(item -> item.getDeptName().equals("党群工作部"))
                                .filter(item -> item.getUserRole().equals("部门正职领导"))
                                .collect(Collectors.toList());
                    } else if (obj.getCheckUserType().equals("公司办正职领导")) {
                        list = checkUserList.stream()
                                .filter(item -> item.getDeptName().equals("公司办"))
                                .filter(item -> item.getUserRole().equals("部门正职领导"))
                                .collect(Collectors.toList());
                    } else if (obj.getCheckUserType().equals("人力资源部正职领导")) {
                        list = checkUserList.stream()
                                .filter(item -> item.getDeptName().equals("人力资源部"))
                                .filter(item -> item.getUserRole().equals("部门正职领导"))
                                .collect(Collectors.toList());
                    } else if (obj.getCheckUserType().equals("纪监法审部正职领导")) {
                        list = checkUserList.stream()
                                .filter(item -> item.getDeptName().equals("纪监法审部"))
                                .filter(item -> item.getUserRole().equals("部门正职领导"))
                                .collect(Collectors.toList());
                    } else if (obj.getCheckUserType().equals("一般党员")) {
                        //党支部下的部门
                        Set<Integer> deptIdSet = partyList.stream().filter(item -> item.getPartyName().equals(checkkUser.getPartyName())).map(Party::getDeptId).collect(Collectors.toSet());
                        //四大部门的正职领导
                        Set<String> deptNameSet = new HashSet<>();
                        deptNameSet.add("党群工作部");
                        deptNameSet.add("公司办");
                        deptNameSet.add("人力资源部");
                        deptNameSet.add("纪监法审部");
                        Set<String> excludeNameSet = checkUserList.stream()
                                .filter(item -> deptNameSet.contains(item.getDeptName()))
                                .filter(item -> item.getUserRole().equals("部门正职领导"))
                                .map(CheckUser::getName).collect(Collectors.toSet());

                        list = checkUserList.stream()
                                .filter(item -> deptIdSet.contains(item.getDeptId()))
                                .filter(item -> item.getPartyRole() != null && item.getPartyRole().equals("一般党员") && !item.getUserRole().equals("特别人员"))
                                .filter(item -> !excludeNameSet.contains(item.getName()))
                                .collect(Collectors.toList());
                    } else {
                        throw new PageTipException(obj.getCheckUserType() + "--错误");
                    }
                    partyAdd(year, obj, checkkUser, list);
                }
            }
        }
        return true;
    }


}
