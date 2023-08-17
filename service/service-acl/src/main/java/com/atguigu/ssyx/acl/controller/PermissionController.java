package com.atguigu.ssyx.acl.controller;

import com.atguigu.ssyx.acl.service.PermissionService;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.acl.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/acl/permission")
@Api(tags="菜单接口")
@CrossOrigin
public class PermissionController {

    @Autowired
    private PermissionService permissionService;


    @ApiOperation("获取所有菜单")
    @GetMapping("")
    public Result list(){
        List<Permission> data = permissionService.getPermissionList();
        return Result.ok(data);
    }

    @ApiOperation("新增一个菜单")
    @PostMapping("/save")
    public Result save(@RequestBody Permission permission){
        permissionService.save(permission);
        return Result.ok(null);
    }

    @ApiOperation("删除一个菜单(包括子菜单)")
    @DeleteMapping("/remove/{id}")
    public Result delete(@PathVariable Long id){
        permissionService.deletePermissionById(id);
        return Result.ok(null);
    }

    @ApiOperation("更新一个菜单")
    @PutMapping("/update")
    public Result update(@RequestBody Permission permission){
        permissionService.updateById(permission);
        return Result.ok(null);
    }

    @ApiOperation("查看某个角色管理的菜单")
    @GetMapping("/toAssign/{roleId}")
    public Result getPermissionByRoleId(@PathVariable Long roleId){
        List<Permission> data = permissionService.getPermissionByRoleId(roleId);
        return Result.ok(data);
    }

    @ApiOperation("给某个角色授权")
    @PostMapping("doAssign")
    public Result doAssign(@RequestParam("roleId") Long roleId, @RequestParam("permissionId") Long permissionId){
        permissionService.doAssign(roleId, permissionId);
        return Result.ok(null);
    }

}