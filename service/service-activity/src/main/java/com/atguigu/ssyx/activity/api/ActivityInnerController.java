package com.atguigu.ssyx.activity.api;

import com.atguigu.ssyx.activity.service.ActivityInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
