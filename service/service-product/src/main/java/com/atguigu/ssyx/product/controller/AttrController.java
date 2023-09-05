package com.atguigu.ssyx.product.controller;


import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.product.Attr;
import com.atguigu.ssyx.product.service.AttrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * <p>
 * 商品属性 前端控制器
 * </p>
 *
 * @author syh
 * @since 2023-08-20
 */
@RestController
@RequestMapping("/admin/product/attr")
@Api(tags = "平台属性接口")

public class AttrController {
    @Autowired
    private AttrService attrService;

    @ApiOperation("根据分组id获取属性")
    @GetMapping("{groupId}")
    public Result getListByGroupId(@PathVariable("groupId") Long groupId){
        List<Attr> data = attrService.getListByGroupId(groupId);
        return Result.ok(data);
    }

    @ApiOperation(value = "获取")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        Attr attr = attrService.getById(id);
        return Result.ok(attr);
    }

    @ApiOperation(value = "新增")
    @PostMapping("save")
    public Result save(@RequestBody Attr attr) {
        attrService.save(attr);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改")
    @PutMapping("update")
    public Result updateById(@RequestBody Attr attr) {
        attrService.updateById(attr);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        attrService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        attrService.removeByIds(idList);
        return Result.ok(null);
    }
}

