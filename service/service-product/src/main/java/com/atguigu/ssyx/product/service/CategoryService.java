package com.atguigu.ssyx.product.service;

import com.atguigu.ssyx.model.product.Category;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品三级分类 服务类
 * </p>
 *
 * @author syh
 * @since 2023-08-20
 */
public interface CategoryService extends IService<Category> {

    Page<Category> getPageList(Page<Category> pageParam, String keyword);

    List<Category> findAllList();
}
