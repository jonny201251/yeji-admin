package com.haiying.yeji.mapper;

import com.haiying.yeji.model.entity.DeptUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 评分部门人员的单向和双向设置 Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2021-12-04
 */
@Mapper
public interface DeptUserMapper extends BaseMapper<DeptUser> {

}
