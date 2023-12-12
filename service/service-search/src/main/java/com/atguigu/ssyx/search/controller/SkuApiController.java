package com.atguigu.ssyx.search.controller;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.search.SkuEs;
import com.atguigu.ssyx.search.service.SkuService;
import com.atguigu.ssyx.vo.search.SkuEsQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search/sku")
@Api(tags = "商品上下架处理接口")
public class SkuApiController {
    @Autowired
    private SkuService skuService;

    @ApiOperation("商品上架")
    @GetMapping("/inner/upperSku/{skuId}")
    public Result upperSku(@PathVariable Long skuId){
        skuService.upperSku(skuId);
        return Result.ok(null);
    }

    @ApiOperation("商品下架")
    @GetMapping("/inner/lowerSku/{skuId}")
    public Result lowerSku(@PathVariable Long skuId){
        skuService.lowerSku(skuId);
        return Result.ok(null);
    }

    @ApiOperation(value = "获取爆品商品")
    @GetMapping("/inner/findHotSkuList")
    public List<SkuEs> findHotSkuList() {
        return skuService.findHotSkuList();
    }

    @ApiOperation("获取相关分类商品")
    @GetMapping("/{curPage}/{size}")
    public Result getCategorySkus(@PathVariable Integer curPage, @PathVariable Integer size, Integer page, Integer limit, SkuEsQueryVo param){
        Page<SkuEs> pageModel = skuService.getCategorySkus(curPage, size, param);
        return Result.ok(pageModel);
    }

    @ApiOperation("更新商品热度")
    @GetMapping("/inner/incrHotScore/{skuId}")
    public Boolean incrHotScore(@PathVariable("skuId") Long skuId){
        return skuService.incrHotScore(skuId);
    }
}

































