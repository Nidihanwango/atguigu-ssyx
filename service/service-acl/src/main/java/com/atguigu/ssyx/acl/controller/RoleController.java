package com.atguigu.ssyx.acl.controller;

import com.atguigu.ssyx.acl.service.RoleService;
import com.atguigu.ssyx.model.acl.Role;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.vo.acl.RoleQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "角色接口")
@RestController
@RequestMapping("/admin/acl/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation("角色列表,分页条件查询")
    @GetMapping("/{page}/{limit}")
    public Result list(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit, RoleQueryVo vo) {
        IPage<Role> pageModel = roleService.pageConditionQuery(page, limit, vo);
        return Result.ok(pageModel);
    }

    @ApiOperation("保存一个新角色")
    @PostMapping("/save")
    public Result save(@RequestBody Role role) {
        boolean isSuccess = roleService.save(role);
        return isSuccess ? Result.ok(null) : Result.fail();
    }

    @ApiOperation("根据id获取角色")
    @GetMapping("/get/{id}")
    public Result getById(@PathVariable Long id) {
        Role role = roleService.getById(id);
        return role == null ? Result.fail() : Result.ok(role);
    }

    @ApiOperation("更新一个角色")
    @PutMapping("/update")
    public Result update(@RequestBody Role role) {
        boolean isSuccess = roleService.updateById(role);
        return isSuccess ? Result.ok(null) : Result.fail();
    }

    @ApiOperation("删除某个角色")
    @DeleteMapping("/remove/{id}")
    public Result delete(@PathVariable Long id) {
        boolean isSuccess = roleService.removeById(id);
        return isSuccess ? Result.ok(null) : Result.fail();
    }

    @ApiOperation("批量删除多个角色")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<Long> ids) {
        boolean isSuccess = roleService.removeByIds(ids);
        return isSuccess ? Result.ok(null) : Result.fail();
    }
}






































