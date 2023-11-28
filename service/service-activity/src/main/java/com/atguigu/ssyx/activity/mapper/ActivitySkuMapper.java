package com.atguigu.ssyx.activity.mapper;

import com.atguigu.ssyx.model.activity.ActivitySku;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 活动参与商品 Mapper 接口
 * </p>
 *
 * @author syh
 * @since 2023-09-05
 */
@Mapper
public interface ActivitySkuMapper extends BaseMapper<ActivitySku> {

    List<Long> getSkuIdsInActivity(@Param("skuIds") List<Long> skuIds);

    List<String> getActivityBySkuId(@Param("skuId") Long skuId);
}
