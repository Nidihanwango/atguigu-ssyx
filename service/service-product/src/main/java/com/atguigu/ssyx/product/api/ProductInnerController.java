package com.atguigu.ssyx.product.api;

import com.atguigu.ssyx.model.product.Category;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.product.service.CategoryService;
import com.atguigu.ssyx.product.service.SkuInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product/inner")
@Api(tags = "商品模块提供内部模块使用接口")
public class ProductInnerController {

    @Autowired
    private SkuInfoService skuInfoService;
    @Autowired
    private CategoryService categoryService;

    @ApiOperation("根据分类id获取分类信息")
    @GetMapping("/getCategory/{categoryId}")
    public Category getCategory(@PathVariable("categoryId") Long categoryId){
        return categoryService.getById(categoryId);
    }

    @ApiOperation("根据skuId获取sku信息")
    @GetMapping("/getSku/{skuId}")
    public SkuInfo getSku(@PathVariable("skuId") Long skuId){
        return skuInfoService.getById(skuId);
    }

    @ApiOperation("根据skuIds批量获取sku信息")
    @PostMapping("/getSkuInfoList")
    public List<SkuInfo> getSkuInfoList(@RequestBody List<Long> skuIds){
        return skuInfoService.listByIds(skuIds);
    }

    @ApiOperation("根据关键字查询sku信息")
    @GetMapping("/getSkuByKeyword/{keyword}")
    public List<SkuInfo> getSkuByKeyword(@PathVariable("keyword") String keyword){
        return skuInfoService.getSkuByKeyword(keyword);
    }

    @ApiOperation("根据分类id集合获取分类信息")
    @PostMapping("/getCategoryList")
    List<Category> getCategoryList(@RequestBody List<Long> rangeIdList){
        return categoryService.listByIds(rangeIdList);
    }

    @ApiOperation("获取所有分类信息")
    @GetMapping("/getAllCategoryList")
    List<Category> getAllCategoryList(){
        return categoryService.list();
    }

    @ApiOperation("获取新人专享商品")
    @GetMapping("/getNewPersonSku")
    List<SkuInfo> getNewPersonSku(){
        return skuInfoService.getNewPersonSku();
    }
}
