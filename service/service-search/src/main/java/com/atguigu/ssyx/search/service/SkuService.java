package com.atguigu.ssyx.search.service;

import com.atguigu.ssyx.model.search.SkuEs;
import com.atguigu.ssyx.vo.search.SkuEsQueryVo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SkuService {
    // 上架商品
    void upperSku(Long skuId);
    // 下架商品
    void lowerSku(Long skuId);
    // 获取爆品商品
    List<SkuEs> findHotSkuList();
    // 获取相关分类商品
    Page<SkuEs> getCategorySkus(Integer page, Integer size, SkuEsQueryVo param);
    // 更新商品热度
    Boolean incrHotScore(Long skuId);
}
