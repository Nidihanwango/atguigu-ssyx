package com.atguigu.ssyx.sys.service;

import com.atguigu.ssyx.model.sys.RegionWare;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 城市仓库关联表 服务类
 * </p>
 *
 * @author syh
 * @since 2023-08-18
 */
public interface RegionWareService extends IService<RegionWare> {

    IPage<RegionWare> getPageList(Integer page, Integer limit, String key);

    void saveRegionWare(RegionWare regionWare);

    void updateStatus(Long id, Integer status);
}
