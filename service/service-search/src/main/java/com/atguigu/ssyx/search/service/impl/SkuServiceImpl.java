package com.atguigu.ssyx.search.service.impl;

import com.atguigu.ssyx.client.activity.ActivityFeignClient;
import com.atguigu.ssyx.client.product.ProductFeignClient;
import com.atguigu.ssyx.common.auth.AuthContextHolder;
import com.atguigu.ssyx.enums.SkuType;
import com.atguigu.ssyx.model.product.Category;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.model.search.SkuEs;
import com.atguigu.ssyx.search.repository.SkuRepository;
import com.atguigu.ssyx.search.service.SkuService;
import com.atguigu.ssyx.vo.search.SkuEsQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SkuServiceImpl implements SkuService {
    @Autowired
    private SkuRepository skuRepository;
    @Autowired
    private ProductFeignClient productFeignClient;
    @Resource
    private ActivityFeignClient activityFeignClient;

    @Override
    public void upperSku(Long skuId) {
        // 1.根据skuId获取sku信息
        SkuInfo skuInfo = productFeignClient.getSku(skuId);
        if (skuInfo == null) {
            throw new RuntimeException("暂无该商品数据");
        }
        // 2.根据分类id获取分类信息
        Category category = productFeignClient.getCategory(skuInfo.getCategoryId());
        if (category == null) {
            throw new RuntimeException("暂无该分类信息");
        }
        // 3.封装SkuEs对象
        SkuEs skuEs = new SkuEs();
        skuEs.setCategoryId(category.getId());
        skuEs.setCategoryName(category.getName());
        skuEs.setId(skuInfo.getId());
        skuEs.setKeyword(skuInfo.getSkuName() + "," + skuEs.getCategoryName());
        skuEs.setWareId(skuInfo.getWareId());
        skuEs.setIsNewPerson(skuInfo.getIsNewPerson());
        skuEs.setImgUrl(skuInfo.getImgUrl());
        skuEs.setTitle(skuInfo.getSkuName());
        if (Objects.equals(skuInfo.getSkuType(), SkuType.COMMON.getCode())) {
            skuEs.setSkuType(0);
            skuEs.setPrice(skuInfo.getPrice().doubleValue());
            skuEs.setStock(skuInfo.getStock());
            skuEs.setSale(skuInfo.getSale());
            skuEs.setPerLimit(skuInfo.getPerLimit());
        } else {
            //TODO 待完善-秒杀商品

        }
        // 4.保存数据到es
        SkuEs save = skuRepository.save(skuEs);
    }

    @Override
    public void lowerSku(Long skuId) {
        skuRepository.deleteById(skuId);
    }

    //获取爆品商品
    @Override
    public List<SkuEs> findHotSkuList() {
        Pageable pageable = PageRequest.of(0, 10);
        return skuRepository.findByOrderByHotScoreDesc(pageable).getContent();
    }

    @Override
    public Page<SkuEs> getCategorySkus(Integer page, Integer size, SkuEsQueryVo param) {
        Page<SkuEs> pageModel = null;
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        param.setWareId(AuthContextHolder.getWareId());
        if (StringUtils.isEmpty(param.getKeyword())) {
            pageModel = skuRepository.findByCategoryIdAndWareId(param.getCategoryId(), param.getWareId(), pageRequest);
        } else {
            pageModel = skuRepository.findByCategoryIdAndWareIdAndKeyword(param.getCategoryId(), param.getWareId(), param.getKeyword(), pageRequest);
        }
        List<SkuEs> content = pageModel.getContent();
        if (!CollectionUtils.isEmpty(content)) {
            List<Long> skuIds = content.stream().map(SkuEs::getId).collect(Collectors.toList());
            Map<Long, List<String>> activity = activityFeignClient.findActivity(skuIds);
            if (!activity.isEmpty()) {
                for (SkuEs skuEs : content) {
                    skuEs.setRuleList(activity.get(skuEs.getId()));
                }
            }
        }
        return pageModel;
    }
}
