package com.atguigu.ssyx.index.service.impl;

import com.atguigu.ssyx.client.activity.ActivityFeignClient;
import com.atguigu.ssyx.client.product.ProductFeignClient;
import com.atguigu.ssyx.client.search.SearchFeignClient;
import com.atguigu.ssyx.common.auth.AuthContextHolder;
import com.atguigu.ssyx.index.service.ItemService;
import com.atguigu.ssyx.vo.product.SkuInfoVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

// 商品详情业务实现类
@Service
public class ItemServiceImpl implements ItemService {

    @Resource
    private ProductFeignClient productFeignClient;
    @Resource
    private SearchFeignClient searchFeignClient;
    @Resource
    private ActivityFeignClient activityFeignClient;
    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    // 根据skuId获取sku详情
    @Override
    public Map<String, Object> getItemDetail(Long skuId) {
        Map<String, Object> result = new HashMap<>();
        // 1.远程调用service-product模块,获取sku基础信息
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            SkuInfoVo skuInfoVo = productFeignClient.getSkuInfoVo(skuId);
            result.put("skuInfoVo", skuInfoVo);
        }, threadPoolExecutor);
        // 2.远程调用service-activity模块,获取sku营销和优惠活动信息
        Long userId = AuthContextHolder.getUserId();
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            Map<String, Object> activityAndCouponMap = activityFeignClient.getSkuActivityAndCoupon(skuId, userId);
            result.putAll(activityAndCouponMap);
        }, threadPoolExecutor);
        // 3.远程调用service-search模块,每次点击商品为商品增加热度
        CompletableFuture<Void> future3 = CompletableFuture.runAsync(() -> {
            searchFeignClient.incrHotScore(skuId);
        }, threadPoolExecutor);
        // 4.等待所有异步任务执行完成
        CompletableFuture.allOf(future1, future2, future3).join();
        return result;
    }
}





























