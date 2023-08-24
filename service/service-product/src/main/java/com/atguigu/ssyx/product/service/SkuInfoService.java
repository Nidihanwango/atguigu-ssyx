package com.atguigu.ssyx.product.service;

import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.vo.product.SkuInfoQueryVo;
import com.atguigu.ssyx.vo.product.SkuInfoVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
