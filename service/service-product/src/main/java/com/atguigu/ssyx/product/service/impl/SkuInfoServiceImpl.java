package com.atguigu.ssyx.product.service.impl;

import com.atguigu.ssyx.model.product.SkuAttrValue;
import com.atguigu.ssyx.model.product.SkuImage;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.model.product.SkuPoster;
import com.atguigu.ssyx.product.mapper.SkuInfoMapper;
import com.atguigu.ssyx.product.service.SkuAttrValueService;
import com.atguigu.ssyx.product.service.SkuImageService;
import com.atguigu.ssyx.product.service.SkuInfoService;
import com.atguigu.ssyx.product.service.SkuPosterService;
import com.atguigu.ssyx.vo.product.SkuInfoQueryVo;
import com.atguigu.ssyx.vo.product.SkuInfoVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * sku信息 服务实现类
 * </p>
 *
 * @author syh
 * @since 2023-08-20
 */
@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo> implements SkuInfoService {

    @Autowired
    private SkuPosterService skuPosterService;
    @Autowired
    private SkuAttrValueService skuAttrValueService;
    @Autowired
    private SkuImageService skuImageService;

    @Override
    public Page<SkuInfo> getPageList(Page<SkuInfo> pageParam, SkuInfoQueryVo skuInfoQueryVo) {
        LambdaQueryWrapper<SkuInfo> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(skuInfoQueryVo.getKeyword())) {
            wrapper.like(SkuInfo::getSkuName, skuInfoQueryVo.getKeyword());
        }
        if (!StringUtils.isEmpty(skuInfoQueryVo.getSkuType())) {
            wrapper.eq(SkuInfo::getSkuType, skuInfoQueryVo.getSkuType());
        }
        if (!StringUtils.isEmpty(skuInfoQueryVo.getCategoryId())) {
            wrapper.eq(SkuInfo::getCategoryId, skuInfoQueryVo.getCategoryId());
        }
        return this.page(pageParam, wrapper);
    }

    @Override
    public void insertSkuinfo(SkuInfoVo skuInfoVo) {
        // 1.添加sku基本信息
        SkuInfo skuInfo = new SkuInfo();
        BeanUtils.copyProperties(skuInfoVo, skuInfo);
        this.save(skuInfo);
        // 2.添加sku属性值
        List<SkuAttrValue> skuAttrValueList = skuInfoVo.getSkuAttrValueList();
        if (!CollectionUtils.isEmpty(skuAttrValueList)) {
            skuAttrValueList.forEach(skuAttrValue -> {
                skuAttrValue.setSkuId(skuInfo.getId());
            });
            skuAttrValueService.saveBatch(skuAttrValueList);
        }
        // 3.添加sku图片
        List<SkuImage> skuImagesList = skuInfoVo.getSkuImagesList();
        if (!CollectionUtils.isEmpty(skuImagesList)) {
            skuImagesList.forEach(skuImage -> {
                skuImage.setSkuId(skuInfo.getId());
            });
            skuImageService.saveBatch(skuImagesList);
        }
        // 4.添加海报列表
        List<SkuPoster> skuPosterList = skuInfoVo.getSkuPosterList();
        if (!CollectionUtils.isEmpty(skuPosterList)) {
            skuPosterList.forEach(skuPoster -> {
                skuPoster.setSkuId(skuInfo.getId());
            });
            skuPosterService.saveBatch(skuPosterList);
        }
    }
}
