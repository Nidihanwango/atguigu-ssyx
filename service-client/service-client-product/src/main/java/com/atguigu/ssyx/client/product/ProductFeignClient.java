package com.atguigu.ssyx.client.product;

import com.atguigu.ssyx.common.config.FeignConfig;
import com.atguigu.ssyx.model.product.Category;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.vo.product.SkuInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "service-product", configuration = {FeignConfig.class})
// todo 多余配置 FeignConfig
public interface ProductFeignClient {

    @GetMapping("/api/product/inner/getCategory/{categoryId}")
    Category getCategory(@PathVariable("categoryId") Long categoryId);
    @GetMapping("/api/product/inner/getSku/{skuId}")
    SkuInfo getSku(@PathVariable("skuId") Long skuId);
    @GetMapping("/api/product/inner/getSkuByKeyword/{keyword}")
    List<SkuInfo> getSkuByKeyword(@PathVariable("keyword") String keyword);
    @PostMapping("/api/product/inner/getSkuInfoList")
    List<SkuInfo> getSkuInfoList(@RequestBody List<Long> skuIds);
    @PostMapping("/api/product/inner/getCategoryList")
    List<Category> getCategoryList(@RequestBody List<Long> rangeIdList);

    // 获取商品分类
    @GetMapping("/api/product/inner/getNewPersonSku")
    List<SkuInfo> getNewPersonSku();

    // 获取新人专享sku
    @GetMapping("/api/product/inner/getAllCategoryList")
    List<Category> getAllCategoryList();
    // 获取sku基础信息
    @GetMapping("/api/product/inner/getSkuInfoVo/{skuId}")
    SkuInfoVo getSkuInfoVo(@PathVariable("skuId") Long skuId);
}
