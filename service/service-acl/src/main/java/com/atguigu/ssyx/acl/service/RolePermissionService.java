package com.atguigu.ssyx.acl.service;

import com.atguigu.ssyx.model.acl.RolePermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface RolePermissionService extends IService<RolePermission> {
    List<Long> getPermissionIdsByRoleId(Long roleId);
}
