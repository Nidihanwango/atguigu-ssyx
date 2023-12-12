package com.atguigu.ssyx.index.controller;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.index.service.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/api/home/item")
@Api(tags = "商品详情数据接口")
public class ItemApiController {

    @Resource
    private ItemService itemService;

    @GetMapping("/{skuId}")
    @ApiOperation("根据skuId获取sku详情")
    public Result getItemDetail(@PathVariable Long skuId){
        Map<String, Object> map = itemService.getItemDetail(skuId);
        return Result.ok(map);
    }
}
