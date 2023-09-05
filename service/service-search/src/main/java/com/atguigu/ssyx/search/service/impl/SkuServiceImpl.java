package com.atguigu.ssyx.search.service.impl;

import com.atguigu.ssyx.client.product.ProductFeignClient;
import com.atguigu.ssyx.enums.SkuType;
import com.atguigu.ssyx.model.product.Category;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.model.search.SkuEs;
import com.atguigu.ssyx.search.repository.SkuRepository;
import com.atguigu.ssyx.search.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SkuServiceImpl implements SkuService {
    @Autowired
    private SkuRepository skuRepository;
    @Autowired
    private ProductFeignClient productFeignClient;

    @Override
    public void upperSku(Long skuId) {
        // 1.根据skuId获取sku信息
        SkuInfo skuInfo = productFeignClient.getSku(skuId);
        if (skuInfo == null) {
            throw new RuntimeException("暂无该商品数据");
        }
        // 2.根据分类id获取分类信息
        Category category = productFeignClient.getCategory(skuInfo.getCategoryId());
        if (category == null) {
            throw new RuntimeException("暂无该分类信息");
        }
        // 3.封装SkuEs对象
        SkuEs skuEs = new SkuEs();
        skuEs.setCategoryId(category.getId());
        skuEs.setCategoryName(category.getName());
        skuEs.setId(skuInfo.getId());
        skuEs.setKeyword(skuInfo.getSkuName()+","+skuEs.getCategoryName());
        skuEs.setWareId(skuInfo.getWareId());
        skuEs.setIsNewPerson(skuInfo.getIsNewPerson());
        skuEs.setImgUrl(skuInfo.getImgUrl());
        skuEs.setTitle(skuInfo.getSkuName());
        if(Objects.equals(skuInfo.getSkuType(), SkuType.COMMON.getCode())) {
            skuEs.setSkuType(0);
            skuEs.setPrice(skuInfo.getPrice().doubleValue());
            skuEs.setStock(skuInfo.getStock());
            skuEs.setSale(skuInfo.getSale());
            skuEs.setPerLimit(skuInfo.getPerLimit());
        } else {
            //TODO 待完善-秒杀商品

        }
        // 4.保存数据到es
        SkuEs save = skuRepository.save(skuEs);
    }

    @Override
    public void lowerSku(Long skuId) {
        skuRepository.deleteById(skuId);
    }
}
