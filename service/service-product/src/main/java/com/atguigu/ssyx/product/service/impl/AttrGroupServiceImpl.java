package com.atguigu.ssyx.product.service.impl;

import com.atguigu.ssyx.model.product.AttrGroup;
import com.atguigu.ssyx.product.mapper.AttrGroupMapper;
import com.atguigu.ssyx.product.service.AttrGroupService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 属性分组 服务实现类
 * </p>
 *
 * @author syh
 * @since 2023-08-20
 */
@Service
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupMapper, AttrGroup> implements AttrGroupService {

    @Override
    public Page<AttrGroup> getPageList(Page<AttrGroup> pageParam, String keyword) {
        LambdaQueryWrapper<AttrGroup> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(keyword)) {
            wrapper.like(AttrGroup::getName, keyword).or().like(AttrGroup::getRemark, keyword);
        }
        return this.page(pageParam, wrapper);
    }

    @Override
    public List<AttrGroup> findAllList() {
        QueryWrapper<AttrGroup> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        return this.list(wrapper);
    }
}
