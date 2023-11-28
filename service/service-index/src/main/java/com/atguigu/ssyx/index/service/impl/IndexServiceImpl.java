package com.atguigu.ssyx.index.service.impl;

import com.atguigu.ssyx.client.activity.ActivityFeignClient;
import com.atguigu.ssyx.client.product.ProductFeignClient;
import com.atguigu.ssyx.client.search.SearchFeignClient;
import com.atguigu.ssyx.client.user.UserFeignClient;
import com.atguigu.ssyx.index.service.IndexService;
import com.atguigu.ssyx.model.product.Category;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.model.search.SkuEs;
import com.atguigu.ssyx.vo.user.LeaderAddressVo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class IndexServiceImpl implements IndexService {

    @Resource
    private ProductFeignClient productFeignClient;
    @Resource
    private UserFeignClient userFeignClient;
    @Resource
    private SearchFeignClient searchFeignClient;
    @Resource
    private ActivityFeignClient activityFeignClient;

    @Override
    // 获取首页显示数据
    public Map<String, Object> getIndexModel(Long userId) {
        Map<String, Object> result = new HashMap<>();
        // 1.获取提货点信息
        LeaderAddressVo leaderAddressVo = userFeignClient.getUserAddressByUserId(userId);
        result.put("leaderAddressVo", leaderAddressVo);
        // 2.新人专享 远程调用
        List<SkuInfo> newPersonSkuInfoList = productFeignClient.getNewPersonSku();
        result.put("newPersonSkuInfoList", newPersonSkuInfoList);
        // 3.商品分类 远程调用
        List<Category> categoryList = productFeignClient.getAllCategoryList();
        result.put("categoryList", categoryList);
        // 4.热销好货 es
        List<SkuEs> hotSkuList = searchFeignClient.findHotSkuList();
        result.put("hotSkuList", hotSkuList);
        // 判空
        if (CollectionUtils.isEmpty(hotSkuList)) {
            return result;
        }
        List<Long> skuIds = hotSkuList.stream().map(SkuEs::getId).collect(Collectors.toList());
        // 获取sku对应的促销活动标签
        Map<Long, List<String>> activity = activityFeignClient.findActivity(skuIds);
        if (activity.isEmpty()) {
            return result;
        }
        for (SkuEs skuEs : hotSkuList) {
            skuEs.setRuleList(activity.get(skuEs.getId()));
        }
        result.put("hotSkuList", hotSkuList);
        //TODO 获取用户首页秒杀数据
        return result;
    }

}
