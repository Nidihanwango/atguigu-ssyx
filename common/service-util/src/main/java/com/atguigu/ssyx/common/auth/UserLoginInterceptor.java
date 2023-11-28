package com.atguigu.ssyx.common.auth;

import com.atguigu.ssyx.common.constant.RedisConst;
import com.atguigu.ssyx.common.utils.JwtHelper;
import com.atguigu.ssyx.vo.user.UserLoginVo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 会员用户登录拦截器
public class UserLoginInterceptor implements HandlerInterceptor {
    private RedisTemplate redisTemplate;

    public UserLoginInterceptor(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return checkUserLogin(request);
    }

    private boolean checkUserLogin(HttpServletRequest request) {
        // 1.从请求头中获取token
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            return false;
        }
        // 2.根据token获取userId
        Long userId = JwtHelper.getUserId(token);
        // 3.从redis中取出登录用户信息
        UserLoginVo userLoginVo = (UserLoginVo) redisTemplate.opsForValue().get(RedisConst.USER_LOGIN_KEY_PREFIX + userId);
        if (userLoginVo == null) {
            return false;
        }
        // 4.将用户信息存入ThreadLocal中
        AuthContextHolder.setUserId(userLoginVo.getUserId());
        AuthContextHolder.setWareId(userLoginVo.getWareId());
        return true;
    }
}
