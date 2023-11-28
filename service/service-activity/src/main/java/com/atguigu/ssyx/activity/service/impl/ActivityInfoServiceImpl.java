package com.atguigu.ssyx.activity.service.impl;

import com.atguigu.ssyx.activity.mapper.ActivityInfoMapper;
import com.atguigu.ssyx.activity.mapper.ActivityRuleMapper;
import com.atguigu.ssyx.activity.mapper.ActivitySkuMapper;
import com.atguigu.ssyx.activity.service.ActivityInfoService;
import com.atguigu.ssyx.client.product.ProductFeignClient;
import com.atguigu.ssyx.model.activity.ActivityInfo;
import com.atguigu.ssyx.model.activity.ActivityRule;
import com.atguigu.ssyx.model.activity.ActivitySku;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.vo.activity.ActivityRuleVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 活动表 服务实现类
 * </p>
 *
 * @author syh
 * @since 2023-09-05
 */
@Service
public class ActivityInfoServiceImpl extends ServiceImpl<ActivityInfoMapper, ActivityInfo> implements ActivityInfoService {

    @Autowired
    private ActivityRuleMapper activityRuleMapper;
    @Autowired
    private ActivitySkuMapper activitySkuMapper;
    @Autowired
    private ProductFeignClient productFeignClient;

    @Override
    public IPage<ActivityInfo> selectPage(Page<ActivityInfo> pageParam) {
        Page<ActivityInfo> pageModel = this.page(pageParam);
        List<ActivityInfo> records = pageModel.getRecords();
        records.forEach(item -> {
            item.setActivityTypeString(item.getActivityType().getComment());
        });
        return pageModel;
    }

    @Override
    public Map<String, Object> findActivityRuleList(Long id) {
        Map<String, Object> map = new HashMap<>();
        // 1.获取活动关联的规则数据
        List<ActivityRule> activityRuleList = activityRuleMapper.selectList(new LambdaQueryWrapper<ActivityRule>().eq(ActivityRule::getActivityId, id));
        map.put("activityRuleList", activityRuleList);
        // 2.获取活动关联的商品sku数据
        // 2.1 获取活动关联的所有商品id
        List<ActivitySku> activitySkuList = activitySkuMapper.selectList(new LambdaQueryWrapper<ActivitySku>().eq(ActivitySku::getActivityId, id));
        List<Long> skuIds = activitySkuList.stream().map(ActivitySku::getSkuId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(skuIds)) {
            return map;
        }
        // 2.2 远程调用product模块获取sku数据
        List<SkuInfo> skuInfoList = productFeignClient.getSkuInfoList(skuIds);
        map.put("skuInfoList", skuInfoList);
        return map;
    }

    @Override
    public void saveActivityRule(ActivityRuleVo activityRuleVo) {
        // 1.删除旧数据 规则&关联商品
        activityRuleMapper.delete(new LambdaQueryWrapper<ActivityRule>().eq(ActivityRule::getActivityId, activityRuleVo.getActivityId()));
        activitySkuMapper.delete(new LambdaQueryWrapper<ActivitySku>().eq(ActivitySku::getActivityId, activityRuleVo.getActivityId()));
        // 2.获取新数据 规则&商品&优惠券
        List<ActivityRule> activityRuleList = activityRuleVo.getActivityRuleList();
        List<ActivitySku> activitySkuList = activityRuleVo.getActivitySkuList();
        // todo 相关优惠券
        List<Long> couponIdList = activityRuleVo.getCouponIdList();
        // 3.获取活动信息
        ActivityInfo activityInfo = this.getById(activityRuleVo.getActivityId());
        // 4.遍历新数据集合,修改并保存
        activityRuleList.forEach(item -> {
            item.setActivityId(activityRuleVo.getActivityId());
            item.setActivityType(activityInfo.getActivityType());
            activityRuleMapper.insert(item);
        });
        activitySkuList.forEach(item -> {
            item.setActivityId(activityRuleVo.getActivityId());
            activitySkuMapper.insert(item);
        });
    }

    // 根据关键词获取sku信息
    @Override
    public List<SkuInfo> findSkuInfoByKeyword(String keyword) {
        // 1.远程调用product模块根据关键字查询sku
        List<SkuInfo> skuInfoList = productFeignClient.getSkuByKeyword(keyword);
        if (CollectionUtils.isEmpty(skuInfoList)) {
            return null;
        }
        // 2.判断商品是否参加过营销活动,活动是否正在进行中
        List<Long> skuIds = skuInfoList.stream().map(SkuInfo::getId).collect(Collectors.toList());
        List<Long> inActivitySkuIds = activitySkuMapper.getSkuIdsInActivity(skuIds);
        // 3.筛选出未参与营销活动的商品返回
        List<SkuInfo> result = new ArrayList<>();
        for (SkuInfo skuInfo : skuInfoList) {
            if (!inActivitySkuIds.contains(skuInfo.getId())) {
                result.add(skuInfo);
            }
        }
        return result;
    }

    // 根据skuId列表获取营销活动信息
    @Override
    public Map<Long, List<String>> findActivity(List<Long> skuIdList) {
        Map<Long, List<String>> result = new HashMap<>();
        for (Long skuId : skuIdList) {
            // 根据skuId查询活动名
            List<String> activityIdsBySkuId = activitySkuMapper.getActivityBySkuId(skuId);
            result.put(skuId, activityIdsBySkuId);
        }
        return result;
    }
}
