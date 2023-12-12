package com.atguigu.ssyx.activity.mapper;

import com.atguigu.ssyx.model.activity.CouponInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 优惠券信息 Mapper 接口
 * </p>
 *
 * @author syh
 * @since 2023-09-05
 */
@Mapper
public interface CouponInfoMapper extends BaseMapper<CouponInfo> {
    // 根据skuId, userId, categoryId获取商品优惠券
    List<CouponInfo> selectCouponInfoList(@Param("skuId") Long skuId, @Param("userId") Long userId, @Param("categoryId") Long categoryId);
}
