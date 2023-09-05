package com.atguigu.ssyx.search.controller;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.search.service.SkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search/sku/inner")
@Api(tags = "商品上下架处理接口")
public class SkuApiController {
    @Autowired
    private SkuService skuService;

    @ApiOperation("商品上架")
    @GetMapping("/upperSku/{skuId}")
    public Result upperSku(@PathVariable Long skuId){
        skuService.upperSku(skuId);
        return Result.ok(null);
    }

    @ApiOperation("商品下架")
    @GetMapping("/lowerSku/{skuId}")
    public Result lowerSku(@PathVariable Long skuId){
        skuService.lowerSku(skuId);
        return Result.ok(null);
    }
}
