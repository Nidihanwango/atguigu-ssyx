package com.atguigu.ssyx.activity.service.impl;

import com.atguigu.ssyx.activity.mapper.CouponInfoMapper;
import com.atguigu.ssyx.activity.mapper.CouponRangeMapper;
import com.atguigu.ssyx.activity.service.CouponInfoService;
import com.atguigu.ssyx.client.product.ProductFeignClient;
import com.atguigu.ssyx.enums.CouponRangeType;
import com.atguigu.ssyx.model.activity.CouponInfo;
import com.atguigu.ssyx.model.activity.CouponRange;
import com.atguigu.ssyx.model.product.Category;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.vo.activity.CouponRuleVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 优惠券信息 服务实现类
 * </p>
 *
 * @author syh
 * @since 2023-09-05
 */
@Service
public class CouponInfoServiceImpl extends ServiceImpl<CouponInfoMapper, CouponInfo> implements CouponInfoService {

    @Autowired
    private CouponRangeMapper couponRangeMapper;
    @Autowired
    private ProductFeignClient productFeignClient;
    @Override
    public IPage<CouponInfo> getPageList(IPage<CouponInfo> pageParam) {
        LambdaQueryWrapper<CouponInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(CouponInfo::getId);
        IPage<CouponInfo> pageModel = this.page(pageParam, wrapper);
        pageModel.getRecords().forEach(item -> {
            item.setCouponTypeString(item.getCouponType().getComment());
            if (item.getRangeType() != null) {
                item.setRangeTypeString(item.getRangeType().getComment());
            }
        });
        return pageModel;
    }

    @Override
    public Map<String, Object> findCouponRuleList(Long couponId) {
        Map<String, Object> result = new HashMap<>();
        // 1.获取优惠券信息
        CouponInfo couponInfo = this.getById(couponId);
        // 2.获取优惠券使用范围
        LambdaQueryWrapper<CouponRange> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CouponRange::getCouponId, couponId);
        List<CouponRange> activitySkuList = couponRangeMapper.selectList(wrapper);
        // 3.根据优惠券的范围类型 分别做处理
        List<Long> rangeIdList = activitySkuList.stream().map(CouponRange::getRangeId).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(rangeIdList)) {
            if(couponInfo.getRangeType() == CouponRangeType.SKU) {
                List<SkuInfo> skuInfoList = productFeignClient.getSkuInfoList(rangeIdList);
                result.put("skuInfoList", skuInfoList);

            } else if (couponInfo.getRangeType() == CouponRangeType.CATEGORY) {
                List<Category> categoryList = productFeignClient.getCategoryList(rangeIdList);
                result.put("categoryList", categoryList);
            }
        }
        return result;
    }

    @Override
    public void saveCouponRule(CouponRuleVo couponRuleVo) {
        // 1.删除旧数据
        couponRangeMapper.delete(new LambdaQueryWrapper<CouponRange>().eq(CouponRange::getCouponId, couponRuleVo.getCouponId()));
        // 2.更新coupon_info
        CouponInfo couponInfo = this.getById(couponRuleVo.getCouponId());
        couponInfo.setRangeType(couponRuleVo.getRangeType());
        couponInfo.setConditionAmount(couponRuleVo.getConditionAmount());
        couponInfo.setAmount(couponRuleVo.getAmount());
        couponInfo.setConditionAmount(couponRuleVo.getConditionAmount());
        couponInfo.setRangeDesc(couponRuleVo.getRangeDesc());
        this.updateById(couponInfo);
        // 3.新增coupon_range
        couponRuleVo.getCouponRangeList().forEach(item -> {
            item.setCouponId(couponRuleVo.getCouponId());
            couponRangeMapper.insert(item);
        });
    }
}
