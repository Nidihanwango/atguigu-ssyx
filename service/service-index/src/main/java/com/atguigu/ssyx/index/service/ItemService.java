package com.atguigu.ssyx.index.service;

import java.util.Map;

/**
 * 商品详情业务接口
 */
public interface ItemService {
    // 根据skuId获取sku详情
    Map<String, Object> getItemDetail(Long skuId);
}
