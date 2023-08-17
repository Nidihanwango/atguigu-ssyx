package com.atguigu.ssyx.acl.service.impl;

import com.atguigu.ssyx.acl.mapper.AdminMapper;
import com.atguigu.ssyx.acl.service.AdminRoleService;
import com.atguigu.ssyx.acl.service.AdminService;
import com.atguigu.ssyx.acl.service.RoleService;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.acl.Admin;
import com.atguigu.ssyx.model.acl.AdminRole;
import com.atguigu.ssyx.model.acl.Role;
import com.atguigu.ssyx.vo.acl.AdminQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Autowired
    private RoleService roleService;
    @Autowired
    private AdminRoleService adminRoleService;

    @Override
    public Result pageList(Integer page, Integer limit, AdminQueryVo vo) {
        IPage<Admin> adminPage = new Page<>(page, limit);
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(vo.getUsername())) {
            wrapper.like("username", vo.getUsername());
        }
        if (!StringUtils.isEmpty(vo.getName())) {
            wrapper.like("name", vo.getName());
        }
        IPage<Admin> list = this.page(adminPage, wrapper);
        return Result.ok(list);
    }

    @Override
    public Map<String, Object> getRolesByAdminId(Long adminId) {
        // 1.获取所有角色
        List<Role> allRolesList = roleService.list();
        // 2.根据用户id获取用户的角色
        // 2.1 从admin_role表中获取用户id为adminId的所有数据
        QueryWrapper<AdminRole> wrapper = new QueryWrapper<>();
        wrapper.eq("admin_id", adminId);
        List<AdminRole> adminRoles = adminRoleService.list(wrapper);
        // 2.2 收集用户角色ids
        List<Long> roleIds = adminRoles.stream().map(AdminRole::getRoleId).collect(Collectors.toList());
        // 2.3 根据用户角色ids获取所有角色
        List<Role> assignRoles = new ArrayList<>();
        for (Role role : allRolesList) {
            if (roleIds.contains(role.getId())) {
                assignRoles.add(role);
            }
        }
        // 3 将结果封装成map返回
        Map<String, Object> map = new HashMap<>();
        map.put("allRolesList", allRolesList);
        map.put("assignRoles", assignRoles);
        return map;
    }

    @Override
    public void doAssign(Long adminId, String roleId) {
        // 1.删除adminId的所有角色关系
        adminRoleService.remove(new QueryWrapper<AdminRole>().eq("admin_id", adminId));
        // 2.重新为adminId用户分配角色
        List<AdminRole> saveList = new ArrayList<>();
        for (String s : roleId.split(",")) {
            long id = Long.parseLong(s);
            saveList.add(new AdminRole(id, adminId));
        }
        if (!saveList.isEmpty()) {
            adminRoleService.saveBatch(saveList);
        }
    }
}























