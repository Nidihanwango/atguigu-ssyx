package com.atguigu.ssyx.product.service;

import com.atguigu.ssyx.model.product.AttrGroup;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 属性分组 服务类
 * </p>
 *
 * @author syh
 * @since 2023-08-20
 */
public interface AttrGroupService extends IService<AttrGroup> {

    Page<AttrGroup> getPageList(Page<AttrGroup> pageParam, String keyword);

    List<AttrGroup> findAllList();
}
