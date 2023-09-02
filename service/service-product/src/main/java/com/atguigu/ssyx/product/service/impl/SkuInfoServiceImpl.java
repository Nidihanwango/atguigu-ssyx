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
import org.springframework.context.annotation.Bean;
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

    @Override
    public SkuInfoVo getSkuById(Long id) {
        // 1.根据id获取sku基本信息
        SkuInfo skuInfo = this.getById(id);
        // 2.根据id获取sku属性信息
        LambdaQueryWrapper<SkuAttrValue> attrValueWrapper = new LambdaQueryWrapper<>();
        attrValueWrapper.eq(SkuAttrValue::getSkuId, id);
        List<SkuAttrValue> attrValues = skuAttrValueService.list(attrValueWrapper);
        // 3.根据id获取sku图片信息
        LambdaQueryWrapper<SkuImage> skuImageWrapper = new LambdaQueryWrapper<>();
        skuImageWrapper.eq(SkuImage::getSkuId, id);
        List<SkuImage> skuImages = skuImageService.list(skuImageWrapper);
        // 4.根据id获取sku海报信息
        LambdaQueryWrapper<SkuPoster> skuPosterWrapper = new LambdaQueryWrapper<>();
        skuPosterWrapper.eq(SkuPoster::getSkuId, id);
        List<SkuPoster> skuPosters = skuPosterService.list(skuPosterWrapper);
        // 5.封装数据返回结果
        SkuInfoVo skuInfoVo = new SkuInfoVo();
        BeanUtils.copyProperties(skuInfo, skuInfoVo);
        skuInfoVo.setSkuAttrValueList(attrValues);
        skuInfoVo.setSkuImagesList(skuImages);
        skuInfoVo.setSkuPosterList(skuPosters);
        return skuInfoVo;
    }

    @Override
    public void updateSku(SkuInfoVo skuInfoVo) {
        // 1.修改sku基本属性
        SkuInfo skuInfo = new SkuInfo();
        BeanUtils.copyProperties(skuInfoVo, skuInfo);
        this.updateById(skuInfo);
        // 2.修改sku平台属性
        // 2.1 删除原有属性
        LambdaQueryWrapper<SkuAttrValue> skuAttrValueWrapper = new LambdaQueryWrapper<>();
        skuAttrValueWrapper.eq(SkuAttrValue::getSkuId, skuInfoVo.getId());
        skuAttrValueService.remove(skuAttrValueWrapper);
        // 2.2 添加新数据
        skuAttrValueService.saveBatch(skuInfoVo.getSkuAttrValueList());
        // 3.修改sku图片信息
        // 3.1 删除原有数据
        LambdaQueryWrapper<SkuImage> skuImageWrapper = new LambdaQueryWrapper<>();
        skuImageWrapper.eq(SkuImage::getSkuId, skuInfoVo.getId());
        skuImageService.remove(skuImageWrapper);
        // 3.2 添加新数据
        skuImageService.saveBatch(skuInfoVo.getSkuImagesList());
        // 4.修改sku海报信息
        LambdaQueryWrapper<SkuPoster> skuPosterWrapper = new LambdaQueryWrapper<>();
        skuPosterWrapper.eq(SkuPoster::getSkuId, skuInfoVo.getId());
        skuPosterService.remove(skuPosterWrapper);
        skuPosterService.saveBatch(skuInfoVo.getSkuPosterList());
    }

    @Override
    public void publishSku(Long id, Integer status) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setId(id);
        skuInfo.setPublishStatus(status);
        this.updateById(skuInfo);
    }

    @Override
    public void checkSku(Long id, Integer status) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setId(id);
        skuInfo.setCheckStatus(status);
        this.updateById(skuInfo);
    }

    @Override
    public void isNewPerson(Long id, Integer status) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setId(id);
        skuInfo.setIsNewPerson(status);
        this.updateById(skuInfo);
    }
}
