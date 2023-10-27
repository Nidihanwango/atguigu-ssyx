package com.atguigu.ssyx.user.service;

import java.util.Map;

public interface WeiXinApiService {
    Map<String, Object> wxLogin(String code);
}
