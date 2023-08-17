package com.atguigu.ssyx.acl.service.impl;

import com.atguigu.ssyx.acl.mapper.PermissionMapper;
import com.atguigu.ssyx.acl.service.PermissionService;
import com.atguigu.ssyx.acl.service.RolePermissionService;
import com.atguigu.ssyx.model.acl.Permission;
import com.atguigu.ssyx.model.acl.RolePermission;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Autowired
    private RolePermissionService rolePermissionService;

    @Override
    public void deletePermissionById(Long id) {
        // 1.获取所有子权限
        List<Permission> allPermissions = this.list();
        List<Long> childrenIds = new ArrayList<>();
        childrenIds.add(id);
        getChildrenIds(id, allPermissions, childrenIds);

        // 2.批量删除所有选中权限
        this.removeByIds(childrenIds);
    }

    private void getChildrenIds(Long id, List<Permission> allPermissions, List<Long> childrenIds) {
        // 获取权限的所有子权限
        allPermissions.stream().filter(permission -> Objects.equals(permission.getPid(), id)).forEach(permission -> {
            Long permissionId = permission.getId();
            childrenIds.add(permissionId);
            getChildrenIds(permissionId, allPermissions, childrenIds);
        });
    }

    @Override
    public List<Permission> getPermissionList() {
        // 1.获取所有权限
        List<Permission> allPermissions = this.list();
        // 2.获取所有pid为0的权限
        List<Permission> collect = allPermissions.stream().filter(permission -> permission.getPid() == 0).peek(permission -> permission.setLevel(1)).collect(Collectors.toList());
        collect.forEach(permission -> {
            getChildrenForPermission(permission, allPermissions);
        });
        return collect;
    }

    /**
     * 获取一个权限的子权限
     *
     * @param permission     目标权限对象
     * @param allPermissions 全部权限对象
     */
    private void getChildrenForPermission(Permission permission, List<Permission> allPermissions) {
        permission.setChildren(allPermissions.stream().filter(p -> Objects.equals(p.getPid(), permission.getId())).peek(p -> {
            p.setLevel(permission.getLevel() + 1);
            getChildrenForPermission(p, allPermissions);
        }).collect(Collectors.toList()));
    }

    @Override
    public List<Permission> getPermissionByRoleId(Long roleId) {
        // 1.从关系表中获取所有的permissionId
        List<Long> ids = rolePermissionService.getPermissionIdsByRoleId(roleId);
        // 2.根据ids获取所有的permission
        return this.listByIds(ids);
    }

    @Override
    public void doAssign(Long roleId, String permissionIds) {
        // 1.将该角色之前分配的权限删除
        QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", roleId);
        rolePermissionService.remove(wrapper);
        // 2.重新为该角色分配权限
        String[] split = permissionIds.split(",");
        List<RolePermission> rolePermissions = new ArrayList<>();
        for (String s : split) {
            RolePermission rolePermission = new RolePermission(roleId, Long.parseLong(s));
            rolePermissions.add(rolePermission);
        }
        rolePermissionService.saveBatch(rolePermissions);
    }
}
