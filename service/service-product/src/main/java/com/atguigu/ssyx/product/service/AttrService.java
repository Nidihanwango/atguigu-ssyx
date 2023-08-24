package com.atguigu.ssyx.product.service;

import com.atguigu.ssyx.model.product.Attr;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品属性 服务类
 * </p>
 *
 * @author syh
 * @since 2023-08-20
 */
public interface AttrService extends IService<Attr> {

    List<Attr> getListByGroupId(Long groupId);
}
