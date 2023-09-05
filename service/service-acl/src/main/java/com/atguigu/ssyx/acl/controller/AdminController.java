package com.atguigu.ssyx.acl.controller;

import com.atguigu.ssyx.acl.service.AdminService;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.common.utils.MD5;
import com.atguigu.ssyx.model.acl.Admin;
import com.atguigu.ssyx.vo.acl.AdminQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "用户接口")
@RestController
@RequestMapping("/admin/acl/user")

public class AdminController {

    @Autowired
    private AdminService adminService;

    @ApiOperation("获取后台用户的分页列表(带搜索)")
    @GetMapping("/{page}/{limit}")
    public Result page(@PathVariable("page") Integer page,
                       @PathVariable("limit") Integer limit,
                       AdminQueryVo vo) {
        return adminService.pageList(page, limit, vo);
    }

    @ApiOperation("根据Id获取后台用户")
    @GetMapping("/get/{id}")
    public Result getById(@PathVariable("id") Long id) {
        Admin admin = adminService.getById(id);
        return Result.ok(admin);
    }

    @ApiOperation("保存新用户")
    @PostMapping("/save")
    public Result save(@RequestBody Admin admin) {
        String encrypt = MD5.encrypt(admin.getPassword());
        admin.setPassword(encrypt);
        adminService.save(admin);
        return Result.ok(null);
    }

    @ApiOperation("更新一个用户")
    @PutMapping("/update")
    public Result update(@RequestBody Admin admin) {
        adminService.updateById(admin);
        return Result.ok(null);
    }

    @ApiOperation("删除某个用户")
    @DeleteMapping("/remove/{id}")
    public Result delete(@PathVariable("id") Long id) {
        adminService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation("批量删除多个用户")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<Long> ids){
        adminService.removeByIds(ids);
        return Result.ok(null);
    }

    @ApiOperation("获取某个用户的所有角色")
    @GetMapping("/toAssign/{adminId}")
    public Result getRoles(@PathVariable Long adminId){
        Map<String, Object> map = adminService.getRolesByAdminId(adminId);
        return Result.ok(map);
    }

    @ApiOperation("给某个用户分配角色")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestParam("adminId") Long adminId, @RequestParam("roleId") String roleId){
        adminService.doAssign(adminId, roleId);
        return Result.ok(null);
    }
}
