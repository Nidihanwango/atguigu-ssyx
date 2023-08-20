package com.atguigu.ssyx.sys.service.impl;

import com.atguigu.ssyx.common.exception.SsyxException;
import com.atguigu.ssyx.common.result.ResultCodeEnum;
import com.atguigu.ssyx.model.sys.RegionWare;
import com.atguigu.ssyx.sys.mapper.RegionWareMapper;
import com.atguigu.ssyx.sys.service.RegionWareService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 城市仓库关联表 服务实现类
 * </p>
 *
 * @author syh
 * @since 2023-08-18
 */
@Service
public class RegionWareServiceImpl extends ServiceImpl<RegionWareMapper, RegionWare> implements RegionWareService {

    @Override
    public IPage<RegionWare> getPageList(Integer page, Integer limit, String key) {
        IPage<RegionWare> iPage = new Page<>(page, limit);
        QueryWrapper<RegionWare> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(key)) {
            wrapper.like("region_name", key).or().like("ware_name", key);
        }
        return this.page(iPage, wrapper);
    }

    @Override
    public void saveRegionWare(RegionWare regionWare) {
        // 1.判断是否已经添加过该开通区域
        int count = this.count(new LambdaQueryWrapper<RegionWare>().eq(RegionWare::getRegionId, regionWare.getRegionId()));
        if (count > 0) {
            throw new SsyxException(ResultCodeEnum.REGION_OPEN);
        }
        // 2.添加新开通区域
        this.save(regionWare);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        RegionWare regionWare = this.getById(id);
        regionWare.setStatus(status);
        this.updateById(regionWare);
    }
}
