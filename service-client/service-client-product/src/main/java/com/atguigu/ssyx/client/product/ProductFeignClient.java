package com.atguigu.ssyx.client.product;

import com.atguigu.ssyx.model.product.Category;
import com.atguigu.ssyx.model.product.SkuInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("service-product")
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
}
