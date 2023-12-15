package com.atguigu.ssyx.cart.service.impl;

import com.atguigu.ssyx.cart.service.CartService;
import com.atguigu.ssyx.client.product.ProductFeignClient;
import com.atguigu.ssyx.common.auth.AuthContextHolder;
import com.atguigu.ssyx.common.constant.RedisConst;
import com.atguigu.ssyx.common.exception.SsyxException;
import com.atguigu.ssyx.common.result.ResultCodeEnum;
import com.atguigu.ssyx.enums.SkuType;
import com.atguigu.ssyx.model.order.CartInfo;
import com.atguigu.ssyx.model.product.SkuInfo;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;

@Service
public class CartServiceImpl implements CartService {
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private ProductFeignClient productFeignClient;
    @Override
    // 向购物车添加商品, 使用redis的hash数据结构 (userId: skuId: cartInfo) 实现
    public void addToCart(Long skuId, Integer num) {
        // 1.获取userId
        Long userId = AuthContextHolder.getUserId();
        // 2.查看购物车中是否已经存在该商品
        String key = getCartKey(userId);
        BoundHashOperations<String, String, CartInfo> ops = redisTemplate.boundHashOps(key);
        CartInfo cartInfo = null;
        if (Boolean.TRUE.equals(ops.hasKey(skuId.toString()))) {
            // 购物车中已经存在该商品
            cartInfo = ops.get(skuId.toString());
            int curNum = cartInfo.getSkuNum() + num;
            if (curNum < 1) {
                // 数量小于1,删除商品
                ops.delete(skuId.toString());
                return;
            }
            // 检查是否超买
            if (curNum >= cartInfo.getPerLimit()) {
                throw new SsyxException(ResultCodeEnum.SKU_LIMIT_ERROR);
            }
            // 更新商品数量并选中该商品
            cartInfo.setSkuNum(curNum);
            cartInfo.setCurrentBuyNum(curNum);
            cartInfo.setIsChecked(1);
            cartInfo.setUpdateTime(new Date());
        } else {
            // 第一次添加商品
            num = 1;
            cartInfo = new CartInfo();
            // 远程调用product模块获取商品信息
            SkuInfo skuInfo = productFeignClient.getSku(skuId);
            if (skuInfo == null) {
                throw new SsyxException(ResultCodeEnum.DATA_ERROR);
            }
            cartInfo.setSkuId(skuId);
            cartInfo.setCategoryId(skuInfo.getCategoryId());
            cartInfo.setSkuType(skuInfo.getSkuType());
            cartInfo.setIsNewPerson(skuInfo.getIsNewPerson());
            cartInfo.setUserId(userId);
            cartInfo.setCartPrice(skuInfo.getPrice());
            cartInfo.setSkuNum(num);
            cartInfo.setCurrentBuyNum(num);
            cartInfo.setSkuType(SkuType.COMMON.getCode());
            cartInfo.setPerLimit(skuInfo.getPerLimit());
            cartInfo.setImgUrl(skuInfo.getImgUrl());
            cartInfo.setSkuName(skuInfo.getSkuName());
            cartInfo.setWareId(skuInfo.getWareId());
            cartInfo.setIsChecked(1);
            cartInfo.setStatus(1);
            cartInfo.setCreateTime(new Date());
            cartInfo.setUpdateTime(new Date());
        }
        // 3.更新购物车
        ops.put(skuId.toString(), cartInfo);
    }

    private String getCartKey(Long userId) {
        return RedisConst.USER_KEY_PREFIX + userId + RedisConst.USER_CART_KEY_SUFFIX;
    }
}
