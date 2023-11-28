package com.atguigu.ssyx.user.service;

import com.atguigu.ssyx.model.user.User;
import com.atguigu.ssyx.vo.user.LeaderAddressVo;

import java.util.Map;

public interface WeiXinApiService {
    // 微信用户登录
    Map<String, Object> wxLogin(String code);
    // 更新用户昵称与头像
    void updateUser(User user);

    LeaderAddressVo getLeaderAddressVoByUserId(Long userId);
}
