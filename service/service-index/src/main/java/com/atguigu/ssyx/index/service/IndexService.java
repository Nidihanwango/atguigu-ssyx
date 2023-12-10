package com.atguigu.ssyx.index.service;

import com.atguigu.ssyx.model.product.Category;

import java.util.List;
import java.util.Map;

public interface IndexService {
    // 获取首页显示数据
    Map<String, Object> getIndexModel(Long userId);
    // 获取所有分类
    List<Category> getAllCategory();
}
