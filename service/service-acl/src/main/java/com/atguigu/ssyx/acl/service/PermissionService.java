package com.atguigu.ssyx.acl.service;

import com.atguigu.ssyx.model.acl.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface PermissionService extends IService<Permission> {
    void deletePermissionById(Long id);

    List<Permission> getPermissionList();

    List<Permission> getPermissionByRoleId(Long roleId);

    void doAssign(Long roleId, String permissionIds);
}
