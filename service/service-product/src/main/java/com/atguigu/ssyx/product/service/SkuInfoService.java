package com.atguigu.ssyx.product.service;

import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.vo.product.SkuInfoQueryVo;
import com.atguigu.ssyx.vo.product.SkuInfoVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * sku信息 服务类
 * </p>
 *
 * @author syh
 * @since 2023-08-20
 */
public interface SkuInfoService extends IService<SkuInfo> {

    Page<SkuInfo> getPageList(Page<SkuInfo> pageParam, SkuInfoQueryVo skuInfoQueryVo);

    void insertSkuinfo(SkuInfoVo skuInfoVo);

    SkuInfoVo getSkuById(Long id);

    void updateSku(SkuInfoVo skuInfoVo);

    void publishSku(Long id, Integer status);

    void checkSku(Long id, Integer status);

    void isNewPerson(Long id, Integer status);

    List<SkuInfo> getSkuByKeyword(String keyword);
    // 获取新人专享商品信息
    List<SkuInfo> getNewPersonSku();
}
