package com.atguigu.ssyx.cart.service;

public interface CartService {
    // 向购物车添加商品
    void addToCart(Long skuId, Integer num);
}
