package com.atguigu.ssyx.client.activity;

import com.atguigu.ssyx.common.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import java.util.Map;

@FeignClient(value = "service-activity", configuration = FeignConfig.class)
public interface ActivityFeignClient {
    // 根据skuId列表获取每个商品参加的营销活动
    @PostMapping("/api/activity/inner/findActivity")
    Map<Long, List<String>> findActivity(@RequestBody List<Long> skuIdList);
    // 获取sku营销和优惠活动信息
    @GetMapping("/api/activity/inner/getSkuActivityAndCoupon/{skuId}/{userId}")
    Map<String, Object> getSkuActivityAndCoupon(@PathVariable("skuId") Long skuId, @PathVariable("userId") Long userId);
}
