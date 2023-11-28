package com.atguigu.ssyx.client.search;

import com.atguigu.ssyx.common.config.FeignConfig;
import com.atguigu.ssyx.model.search.SkuEs;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "service-search", configuration = {FeignConfig.class})
public interface SearchFeignClient {
    // 获取爆品商品
    @GetMapping("/api/search/sku/inner/findHotSkuList")
    List<SkuEs> findHotSkuList();
}
