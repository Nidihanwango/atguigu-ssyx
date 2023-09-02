package com.atguigu.ssyx.product.controller;


import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.product.service.SkuInfoService;
import com.atguigu.ssyx.vo.product.SkuInfoQueryVo;
import com.atguigu.ssyx.vo.product.SkuInfoVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * sku信息 前端控制器
 * </p>
 *
 * @author syh
 * @since 2023-08-20
 */
@RestController
@RequestMapping("/admin/product/skuInfo")
@Api(tags = "sku信息接口")
@CrossOrigin
public class SkuInfoController {

    @Autowired
    private SkuInfoService skuInfoService;

    @ApiOperation("分页条件查询")
    @GetMapping("{curPage}/{size}")
    public Result getPageList(@PathVariable("curPage") Integer curPage, @PathVariable("size") Integer size, SkuInfoQueryVo skuInfoQueryVo) {
        Page<SkuInfo> pageParam = new Page<>(curPage, size);
        Page<SkuInfo> pageModel = skuInfoService.getPageList(pageParam, skuInfoQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public Result save(@RequestBody SkuInfoVo skuInfoVo) {
        skuInfoService.insertSkuinfo(skuInfoVo);
        return Result.ok(null);
    }

    @ApiOperation("根据id查询")
    @GetMapping("get/{id}")
    public Result getById(@PathVariable("id") Long id) {
        SkuInfoVo vo = skuInfoService.getSkuById(id);
        return Result.ok(vo);
    }

    @ApiOperation("修改sku信息")
    @PutMapping("update")
    public Result update(@RequestBody SkuInfoVo skuInfoVo) {
        skuInfoService.updateSku(skuInfoVo);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        skuInfoService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        skuInfoService.removeByIds(idList);
        return Result.ok(null);
    }

    @ApiOperation("商品上架")
    @GetMapping("/publish/{id}/{status}")
    public Result publish(@PathVariable("id") Long id, @PathVariable("status") Integer status) {
        skuInfoService.publishSku(id, status);
        return Result.ok(null);
    }

    @ApiOperation("商品审核")
    @GetMapping("/check/{id}/{status}")
    public Result check(@PathVariable("id") Long id, @PathVariable("status") Integer status) {
        skuInfoService.checkSku(id, status);
        return Result.ok(null);
    }

    @ApiOperation("新人专享")
    @GetMapping("/isNewPerson/{id}/{status}")
    public Result isNewPerson(@PathVariable("id") Long id, @PathVariable("status") Integer status) {
        skuInfoService.isNewPerson(id, status);
        return Result.ok(null);
    }
}

