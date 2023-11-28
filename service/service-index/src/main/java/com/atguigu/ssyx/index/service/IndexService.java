package com.atguigu.ssyx.index.service;

import java.util.Map;

public interface IndexService {
    // 获取首页显示数据
    Map<String, Object> getIndexModel(Long userId);
}
