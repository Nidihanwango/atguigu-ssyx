package com.atguigu.ssyx.cart.controller;

import com.atguigu.ssyx.cart.service.CartService;
import com.atguigu.ssyx.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RestController
@RequestMapping("/api/cart")
@Api(tags = "购物车相关接口列表")
public class CartApiController {
    @Resource
    private CartService cartService;

    @GetMapping("/addToCart/{skuId}/{num}")
    @ApiOperation("添加商品到购物车")
    public Result addToCart(@PathVariable("skuId") Long skuId, @PathVariable("num") Integer num){
        cartService.addToCart(skuId, num);
        return Result.ok(null);
    }
}
