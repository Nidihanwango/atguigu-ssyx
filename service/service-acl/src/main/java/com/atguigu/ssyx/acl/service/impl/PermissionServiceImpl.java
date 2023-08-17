package com.atguigu.ssyx.acl.service.impl;

import com.atguigu.ssyx.acl.mapper.PermissionMapper;
import com.atguigu.ssyx.acl.service.PermissionService;
import com.atguigu.ssyx.model.acl.Permission;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Override
    public void deletePermissionById(Long id) {
        // 1.获取所有子菜单
        List<Permission> allPermissions = this.list();
        List<Long> childrenIds = new ArrayList<>();
        childrenIds.add(id);
        getChildrenIds(id, allPermissions, childrenIds);

        // 2.批量删除所有选中菜单
        this.removeByIds(childrenIds);
    }

    private void getChildrenIds(Long id, List<Permission> allPermissions, List<Long> childrenIds) {
        // 获取菜单的所有子菜单
        allPermissions.stream().filter(permission -> Objects.equals(permission.getPid(), id)).forEach(permission -> {
            Long permissionId = permission.getId();
            childrenIds.add(permissionId);
            getChildrenIds(permissionId, allPermissions, childrenIds);
        });
    }

    @Override
    public List<Permission> getPermissionList() {
        // 1.获取所有菜单
        List<Permission> allPermissions = this.list();
        // 2.获取所有pid为0的菜单
        List<Permission> collect = allPermissions.stream().filter(permission -> permission.getPid() == 0).peek(permission -> permission.setLevel(1)).collect(Collectors.toList());
        collect.forEach(permission -> {
            getChildrenForPermission(permission, allPermissions);
        });
        return collect;
    }

    /**
     * 获取一个菜单的子菜单
     * @param permission 目标菜单对象
     * @param allPermissions 全部菜单对象
     */
    private void getChildrenForPermission(Permission permission, List<Permission> allPermissions){
        permission.setChildren(allPermissions.stream().filter(p -> Objects.equals(p.getPid(), permission.getId())).peek(p -> {
            p.setLevel(permission.getLevel() + 1);
            getChildrenForPermission(p, allPermissions);
        }).collect(Collectors.toList()));
    }

    @Override
    public List<Permission> getPermissionByRoleId(Long roleId) {
        return null;
    }

    @Override
    public void doAssign(Long roleId, Long permissionId) {

    }
}
