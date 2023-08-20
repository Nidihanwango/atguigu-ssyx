package com.atguigu.ssyx.product.service.impl;

import com.atguigu.ssyx.common.exception.SsyxException;
import com.atguigu.ssyx.model.product.Category;
import com.atguigu.ssyx.product.mapper.CategoryMapper;
import com.atguigu.ssyx.product.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 商品三级分类 服务实现类
 * </p>
 *
 * @author syh
 * @since 2023-08-20
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Override
    public Page<Category> getPageList(Page<Category> pageParam, String keyword) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(keyword)) {
            wrapper.like(Category::getName, keyword);
        }
        return this.page(pageParam, wrapper);
    }

    @Override
    public List<Category> findAllList() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Category::getSort);
        return this.list(wrapper);
    }
}
