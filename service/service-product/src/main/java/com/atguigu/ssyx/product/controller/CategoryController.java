package com.atguigu.ssyx.product.controller;


import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.product.Category;
import com.atguigu.ssyx.product.service.CategoryService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 商品三级分类 前端控制器
 * </p>
 *
 * @author syh
 * @since 2023-08-20
 */
@RestController
@RequestMapping("/admin/product/category")
@Api(tags = "商品分类接口")
@CrossOrigin
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @ApiOperation("分页查询")
    @GetMapping("/{currentPage}/{size}")
    public Result getPageList(@PathVariable("currentPage") Integer currentPage,
                              @PathVariable("size") Integer size,
                              @RequestParam(name = "name", required = false) String keyword){
        Page<Category> pageParam = new Page<>(currentPage, size);
        Page<Category> page = categoryService.getPageList(pageParam, keyword);
        return Result.ok(page);
    }

    @ApiOperation("根据id查询分类数据")
    @GetMapping("/get/{id}")
    public Result getById(@PathVariable("id") Long id){
        Category category = categoryService.getById(id);
        return Result.ok(category);
    }

    @ApiOperation("新增分类数据")
    @PostMapping("/save")
    public Result save(@RequestBody Category category){
        categoryService.save(category);
        return Result.ok(null);
    }

    @ApiOperation("修改分类数据")
    @PutMapping("/update")
    public Result update(@RequestBody Category category){
        categoryService.updateById(category);
        return Result.ok(null);
    }

    @ApiOperation("根据id删除分类数据")
    @DeleteMapping("/remove/{id}")
    public Result removeById(@PathVariable("id") Long id){
        categoryService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation("根据ids批量删除分类数据")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList){
        categoryService.removeByIds(idList);
        return Result.ok(null);
    }

    @ApiOperation("获取全部分类数据")
    @GetMapping("/findAllList")
    public Result findAllList(){
        List<Category> data = categoryService.findAllList();
        return Result.ok(data);
    }
}

