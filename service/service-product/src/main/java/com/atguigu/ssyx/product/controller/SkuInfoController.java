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
    public Result getPageList(@PathVariable("curPage") Integer curPage, @PathVariable("size") Integer size, SkuInfoQueryVo skuInfoQueryVo){
        Page<SkuInfo> pageParam = new Page<>(curPage, size);
        Page<SkuInfo> pageModel = skuInfoService.getPageList(pageParam, skuInfoQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public Result save(@RequestBody SkuInfoVo skuInfoVo){
        skuInfoService.insertSkuinfo(skuInfoVo);
        return Result.ok(null);
    }
}

