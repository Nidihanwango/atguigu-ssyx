package com.atguigu.ssyx.product.service.impl;

import com.atguigu.ssyx.model.product.Attr;
import com.atguigu.ssyx.product.mapper.AttrMapper;
import com.atguigu.ssyx.product.service.AttrService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品属性 服务实现类
 * </p>
 *
 * @author syh
 * @since 2023-08-20
 */
@Service
public class AttrServiceImpl extends ServiceImpl<AttrMapper, Attr> implements AttrService {

    @Override
    public List<Attr> getListByGroupId(Long groupId) {
        LambdaQueryWrapper<Attr> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Attr::getAttrGroupId, groupId);
        return this.list(wrapper);
    }
}
