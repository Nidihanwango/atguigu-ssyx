package com.atguigu.ssyx.product.controller;


import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.product.AttrGroup;
import com.atguigu.ssyx.product.service.AttrGroupService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 属性分组 前端控制器
 * </p>
 *
 * @author syh
 * @since 2023-08-20
 */
@RestController
@RequestMapping("/admin/product/attrGroup")
@Api(tags = "平台属性分组接口")

public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    @ApiOperation("分页查询")
    @GetMapping("/{curPage}/{size}")
    public Result getPageList(@PathVariable("curPage") Integer curPage, @PathVariable("size") Integer size, @RequestParam(value = "name", required = false) String keyword){
        Page<AttrGroup> pageParam = new Page<>(curPage, size);
        Page<AttrGroup> page = attrGroupService.getPageList(pageParam, keyword);
        return Result.ok(page);
    }

    @ApiOperation("查询所有分组")
    @GetMapping("/findAllList")
    public Result findAllList(){
        List<AttrGroup> data = attrGroupService.findAllList();
        return Result.ok(data);
    }
    @ApiOperation(value = "获取")
    @GetMapping("/get/{id}")
    public Result get(@PathVariable Long id) {
        AttrGroup attrGroup = attrGroupService.getById(id);
        return Result.ok(attrGroup);
    }

    @ApiOperation(value = "新增")
    @PostMapping("save")
    public Result save(@RequestBody AttrGroup attrGroup) {
        attrGroupService.save(attrGroup);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改")
    @PutMapping("update")
    public Result updateById(@RequestBody AttrGroup attrGroup) {
        attrGroupService.updateById(attrGroup);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        attrGroupService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        attrGroupService.removeByIds(idList);
        return Result.ok(null);
    }
}

