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
    PartySecretaryService partySecretaryService;
    @Autowired
    SysDeptService sysDeptService;

    private void add(Integer year, String scoreType, CheckkObject checkkObject, CheckUser checkkUser, List<CheckUser> checkUserList) {
        if (ObjectUtil.isNotEmpty(checkUserList)) {
            List<Score> scoreList = new ArrayList<>();
            for (CheckUser checkUser : checkUserList) {
                Score score = new Score();
                score.setYear(year);
                score.setScoreType(scoreType);
                //
                score.setDepttId(checkkUser.getDeptId());
                score.setDepttSort(checkkUser.getDeptSort());
                score.setUserrName(checkkUser.getName());
                score.setUserrType(checkkUser.getUserType());
                score.setUserrSort(checkkUser.getUserSort());
                score.setCheckkObject(checkkObject.getCheckkObject());
                //
                score.setDeptId(checkUser.getDeptId());
                score.setUserName(checkUser.getName());
                score.setUserType(checkUser.getUserType());
                score.setCheckUserType(checkkObject.getCheckUserType());
                score.setCheckUserTypeSort(checkkObject.getId());
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
                .collect(Collectors.toList());
    }

    private List<CheckUser> get2List(List<CheckUser> checkUserList, List<ChargeDeptLeader> chargeDeptLeaderList, CheckUser checkkUser) {
        List<ChargeDeptLeader> list = chargeDeptLeaderList.stream().filter(item -> item.getDeptId().equals(checkkUser.getDeptId())).collect(Collectors.toList());
        Set<String> userNameSet = list.stream().map(ChargeDeptLeader::getUserName).collect(Collectors.toSet());
        return checkUserList.stream().filter(item -> userNameSet.contains(item.getName())).collect(Collectors.toList());
    }

    private List<CheckUser> get3List(List<CheckUser> checkUserList, List<ChargeDeptLeader> chargeDeptLeaderList, CheckUser checkkUser) {
        //先取出主管部门领导
        List<ChargeDeptLeader> list = chargeDeptLeaderList.stream().filter(item -> item.getDeptId().equals(checkkUser.getDeptId())).collect(Collectors.toList());
        Set<String> userNameSet = list.stream().map(ChargeDeptLeader::getUserName).collect(Collectors.toSet());
        //用户类型=公司领导
        List<CheckUser> list2 = checkUserList.stream().filter(item -> item.getUserType().equals("公司领导")).collect(Collectors.toList());
        return list2.stream().filter(item -> !userNameSet.contains(item.getName())).collect(Collectors.toList());
    }

    private List<CheckUser> get4List(List<CheckUser> checkUserList, List<ChargeDeptLeader> chargeDeptLeaderList, CheckUser checkkUser) {
        return checkUserList
                .stream()
                .filter(item -> item.getUserRole().equals("部门正职领导") || item.getUserRole().equals("部门副职领导"))
                .filter(item -> !item.getDeptId().equals(checkkUser.getDeptId()))
                .collect(Collectors.toList());
    }

    private List<CheckUser> get5List(List<CheckUser> checkUserList, List<ChargeDeptLeader> chargeDeptLeaderList, CheckUser checkkUser) {
        return checkUserList
                .stream()
                .filter(item -> item.getDeptId().equals(checkkUser.getDeptId()))
                .filter(item -> !item.getUserRole().equals("部门正职领导") && !item.getUserRole().equals("部门副职领导"))
                .collect(Collectors.toList());
    }

    private List<CheckUser> get6List(List<CheckUser> checkUserList, List<ChargeDeptLeader> chargeDeptLeaderList, CheckUser checkkUser) {
        return checkUserList
                .stream()
                .filter(item -> item.getDeptId().equals(checkkUser.getDeptId()))
                .filter(item -> item.getUserRole().equals("部门正职领导"))
                .collect(Collectors.toList());
    }

    private List<CheckUser> get7List(List<CheckUser> checkUserList, List<ChargeDeptLeader> chargeDeptLeaderList, CheckUser checkkUser) {
        return checkUserList
                .stream()
                .filter(item -> item.getUserRole().equals("部门正职领导") || item.getUserRole().equals("部门副职领导"))
                .filter(item -> item.getDeptId().equals(checkkUser.getDeptId()))
                .collect(Collectors.toList());
    }

    private List<CheckUser> get8List(List<CheckUser> checkUserList, List<ChargeDeptLeader> chargeDeptLeaderList, CheckUser checkkUser) {
        return checkUserList
                .stream()
                .filter(item -> item.getUserRole().equals("一般管理人员"))
                .filter(item -> item.getDeptId().equals(checkkUser.getDeptId()))
                .filter(item -> !item.getName().equals(checkkUser.getName()))
                .collect(Collectors.toList());
    }

    private List<CheckUser> get9List(List<CheckUser> checkUserList, List<ChargeDeptLeader> chargeDeptLeaderList, CheckUser checkkUser) {
        return checkUserList
                .stream()
                .filter(item -> item.getUserRole().equals("班组成员"))
                .filter(item -> item.getDeptId().equals(checkkUser.getDeptId()))
                .filter(item -> !item.getName().equals(checkkUser.getName()))
                .collect(Collectors.toList());
    }

    private List<CheckUser> get10List(List<CheckUser> checkUserList, List<ChargeDeptLeader> chargeDeptLeaderList, CheckUser checkkUser) {
        return checkUserList
                .stream()
                .filter(item -> item.getUserRole().equals("班组长"))
                .filter(item -> item.getDeptId().equals(checkkUser.getDeptId()))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public boolean generate(Integer year) {
        //先删除,后插入
        this.remove(new LambdaQueryWrapper<Score>().eq(Score::getYear, year));
        //
        List<CheckkObject> checkkObjectList = checkkObjectService.list(new LambdaQueryWrapper<CheckkObject>().orderByAsc(CheckkObject::getSort));
        List<CheckUser> checkUserList = checkUserService.list(new LambdaQueryWrapper<CheckUser>().eq(CheckUser::getWorkStatus, "在岗").orderByAsc(Arrays.asList(CheckUser::getDeptSort, CheckUser::getUserSort)));
        List<ChargeDeptLeader> chargeDeptLeaderList = chargeDeptLeaderService.list(new LambdaQueryWrapper<ChargeDeptLeader>().orderByAsc(Arrays.asList(ChargeDeptLeader::getUserSort, ChargeDeptLeader::getDeptSort)));
        List<PartySecretary> partySecretaryList = partySecretaryService.list();
        //考核人员
        List<CheckUser> list;
        for (CheckkObject obj : checkkObjectList) {
            list = null;
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
                    add(year, "部门", obj, checkkUser, list);
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
                    add(year, "部门", obj, checkkUser, list);
                }
            } else if (obj.getCheckkObject().equals("部门正职领导")) {
                List<CheckUser> list2 = checkUserList.stream().filter(item -> item.getUserRole().equals(obj.getCheckkObject())).collect(Collectors.toList());
                for (CheckUser checkkUser : list2) {
                    if (obj.getCheckUserType().equals("主管公司领导")) {
                        list = get2List(checkUserList, chargeDeptLeaderList, checkkUser);
                    } else if (obj.getCheckUserType().equals("其他公司领导")) {
                        list = get3List(checkUserList, chargeDeptLeaderList, checkkUser);
                    } else if (obj.getCheckUserType().equals("相关部门正副职领导")) {
                        list = get4List(checkUserList, chargeDeptLeaderList, checkkUser);
                    } else if (obj.getCheckUserType().equals("部门人员")) {
                        list = get5List(checkUserList, chargeDeptLeaderList, checkkUser);
                    } else {
                        throw new PageTipException(obj.getCheckUserType() + "--错误");
                    }
                    add(year, "部门", obj, checkkUser, list);
                }
            } else if (obj.getCheckkObject().equals("部门副职领导")) {
                List<CheckUser> list2 = checkUserList.stream().filter(item -> item.getUserRole().equals(obj.getCheckkObject())).collect(Collectors.toList());
                for (CheckUser checkkUser : list2) {
                    if (obj.getCheckUserType().equals("主管公司领导")) {
                        list = get2List(checkUserList, chargeDeptLeaderList, checkkUser);
                    } else if (obj.getCheckUserType().equals("其他公司领导")) {
                        list = get3List(checkUserList, chargeDeptLeaderList, checkkUser);
                    } else if (obj.getCheckUserType().equals("相关部门正副职领导")) {
                        list = get4List(checkUserList, chargeDeptLeaderList, checkkUser);
                    } else if (obj.getCheckUserType().equals("本部门正职领导")) {
                        list = get6List(checkUserList, chargeDeptLeaderList, checkkUser);
                    } else if (obj.getCheckUserType().equals("部门人员")) {
                        list = get5List(checkUserList, chargeDeptLeaderList, checkkUser);
                    } else {
                        throw new PageTipException(obj.getCheckUserType() + "--错误");
                    }
                    add(year, "部门", obj, checkkUser, list);
                }
            } else if (obj.getCheckkObject().equals("一般管理人员")) {
                List<CheckUser> list2 = checkUserList.stream().filter(item -> item.getUserRole().equals(obj.getCheckkObject())).collect(Collectors.toList());
                for (CheckUser checkkUser : list2) {
                    if (obj.getCheckUserType().equals("本部门正副职领导")) {
                        list = get7List(checkUserList, chargeDeptLeaderList, checkkUser);
                    } else if (obj.getCheckUserType().equals("一般管理人员")) {
                        list = get8List(checkUserList, chargeDeptLeaderList, checkkUser);
                    } else {
                        throw new PageTipException(obj.getCheckUserType() + "--错误");
                    }
                    add(year, "部门", obj, checkkUser, list);
                }
            } else if (obj.getCheckkObject().equals("班组长")) {
                List<CheckUser> list2 = checkUserList.stream().filter(item -> item.getUserRole().equals(obj.getCheckkObject())).collect(Collectors.toList());
                for (CheckUser checkkUser : list2) {
                    if (obj.getCheckUserType().equals("本部门正副职领导")) {
                        list = get7List(checkUserList, chargeDeptLeaderList, checkkUser);
                    } else if (obj.getCheckUserType().equals("班组成员")) {
                        list = get9List(checkUserList, chargeDeptLeaderList, checkkUser);
                    } else {
                        throw new PageTipException(obj.getCheckUserType() + "--错误");
                    }
                    add(year, "部门", obj, checkkUser, list);
                }
            } else if (obj.getCheckkObject().equals("班组成员")) {
                List<CheckUser> list2 = checkUserList.stream().filter(item -> item.getUserRole().equals(obj.getCheckkObject())).collect(Collectors.toList());
                for (CheckUser checkkUser : list2) {
                    if (obj.getCheckUserType().equals("本部门正副职领导")) {
                        list = get7List(checkUserList, chargeDeptLeaderList, checkkUser);
                    } else if (obj.getCheckUserType().equals("班组长")) {
                        list = get10List(checkUserList, chargeDeptLeaderList, checkkUser);
                    } else if (obj.getCheckUserType().equals("班组成员")) {
                        list = get9List(checkUserList, chargeDeptLeaderList, checkkUser);
                    } else {
                        throw new PageTipException(obj.getCheckUserType() + "--错误");
                    }
                    add(year, "部门", obj, checkkUser, list);
                }
            } else if (obj.getCheckkObject().equals("特别人员")) {
                List<CheckUser> list2 = checkUserList.stream().filter(item -> item.getUserRole().equals(obj.getCheckkObject())).collect(Collectors.toList());
                for (CheckUser checkkUser : list2) {
                    if (obj.getCheckUserType().equals("本部门正副职领导")) {
                        list = get7List(checkUserList, chargeDeptLeaderList, checkkUser);
                    } else {
                        throw new PageTipException(obj.getCheckUserType() + "--错误");
                    }
                    add(year, "部门", obj, checkkUser, list);
                }
            } else if (obj.getCheckkObject().equals("党支部书记")) {
                List<CheckUser> list2 = checkUserList.stream().filter(item -> item.getUserRole().equals(obj.getCheckkObject())).collect(Collectors.toList());
                for (CheckUser checkkUser : list2) {
                    List<CheckUser> dataList = new ArrayList<>();
                    //排除人员
                    Set<String> nameSet = new HashSet<>();
                    nameSet.add(checkkUser.getName());
                    //党委书记、党委副书记
                    Set<String> userNameSet = partySecretaryList.stream().map(PartySecretary::getUserName).collect(Collectors.toSet());
                    List<CheckUser> list3 = checkUserList.stream().filter(item -> userNameSet.contains(item.getName())).collect(Collectors.toList());
                    dataList.addAll(list3);
                    //公司领导
                    nameSet.addAll(dataList.stream().map(CheckUser::getName).collect(Collectors.toSet()));
                    List<CheckUser> list4 = checkUserList
                            .stream()
                            .filter(item -> item.getUserType().equals("公司领导"))
                            .filter(item -> !nameSet.contains(item.getName()))
                            .collect(Collectors.toList());
                    dataList.addAll(list4);
                    //党群部正职领导、公司办正职领导、人力资源部正职领导、纪检法审部正职领导
                    nameSet.addAll(dataList.stream().map(CheckUser::getName).collect(Collectors.toSet()));
                    List<SysDept> deptList = sysDeptService.list(new LambdaQueryWrapper<SysDept>().in(SysDept::getName, Arrays.asList("", "", "", "")));
                    Set<Integer> deptSet = deptList.stream().map(SysDept::getId).collect(Collectors.toSet());
                    List<CheckUser> list5 = checkUserList
                            .stream()
                            .filter(item -> item.getUserRole().equals("部门正职领导"))
                            .filter(item -> deptSet.contains(item.getDeptId()))
                            .filter(item -> !nameSet.contains(item.getName()))
                            .collect(Collectors.toList());
                    dataList.addAll(list5);
                    //党支部书记
                    nameSet.addAll(dataList.stream().map(CheckUser::getName).collect(Collectors.toSet()));
                    List<CheckUser> list6 = checkUserList
                            .stream()
                            .filter(item -> item.getPartyRole().equals("专职支部书记") || item.getPartyRole().equals("兼职支部书记"))
                            .filter(item -> !nameSet.contains(item.getName()))
                            .collect(Collectors.toList());
                    dataList.addAll(list6);
                    //一般党员
                    nameSet.addAll(dataList.stream().map(CheckUser::getName).collect(Collectors.toSet()));
                    List<CheckUser> list7 = checkUserList
                            .stream()
                            .filter(item -> item.getPartyRole().equals("一般党员"))
                            .filter(item -> !nameSet.contains(item.getName()))
                            .collect(Collectors.toList());
                    dataList.addAll(list7);
                    add(year, "党支部", obj, checkkUser, dataList);
                }
            }
        }
        return true;
    }


}
