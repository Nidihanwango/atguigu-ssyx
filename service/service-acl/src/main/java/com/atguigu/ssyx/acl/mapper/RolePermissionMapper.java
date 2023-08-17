package com.atguigu.ssyx.acl.mapper;

import com.atguigu.ssyx.model.acl.RolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    List<Long> getPermissionIdsByRoleId(@Param("roleId") Long roleId);
}
