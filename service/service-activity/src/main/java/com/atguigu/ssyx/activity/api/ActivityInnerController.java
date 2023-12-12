package com.atguigu.ssyx.activity.api;

import com.atguigu.ssyx.activity.service.ActivityInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "营销活动内部调用接口")
@RequestMapping("/api/activity/inner")
public class ActivityInnerController {
    @Resource
    private ActivityInfoService activityInfoService;

    @ApiOperation("根据skuId获取对应的营销活动名")
    @PostMapping("/findActivity")
    public Map<Long, List<String>> findActivity(@RequestBody List<Long> skuIdList){
        return activityInfoService.findActivity(skuIdList);
    }

    @ApiOperation("获取sku营销和优惠活动信息")
    @GetMapping("/getSkuActivityAndCoupon/{skuId}/{userId}")
    Map<String, Object> getSkuActivityAndCoupon(@PathVariable("skuId") Long skuId, @PathVariable("userId") Long userId){
        return activityInfoService.getSkuActivityAndCoupon(skuId, userId);
    }
}
