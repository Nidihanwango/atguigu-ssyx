package com.atguigu.ssyx.product.api;

import com.atguigu.ssyx.model.product.Category;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.product.service.CategoryService;
import com.atguigu.ssyx.product.service.SkuInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
