package com.atguigu.ssyx.common.auth;

import com.atguigu.ssyx.vo.acl.AdminLoginVo;
import com.atguigu.ssyx.vo.user.UserLoginVo;

/**
 * 获取登录用户信息类
 */
public class AuthContextHolder {
    // 会员用户id
    private static ThreadLocal<Long> userIdThreadLocal = new ThreadLocal<>();
    // 仓库id
    private static ThreadLocal<Long> wareIdThreadLocal = new ThreadLocal<>();
    // 会员用户基本信息
    private static ThreadLocal<UserLoginVo> userLoginVoThreadLocal = new ThreadLocal<>();
    // 后台管理用户id
    private static ThreadLocal<Long> adminIdThreadLocal = new ThreadLocal<>();
    // 管理员基本信息
    private static ThreadLocal<AdminLoginVo> adminLoginVoThreadLocal = new ThreadLocal<>();

    public static Long getUserId() {
        return userIdThreadLocal.get();
    }

    public static void setUserId(Long userId) {
        userIdThreadLocal.set(userId);
    }

    public static Long getWareId() {
        return wareIdThreadLocal.get();
    }

    public static void setWareId(Long wareId) {
        wareIdThreadLocal.set(wareId);
    }

    public static UserLoginVo getUserLoginVo() {
        return userLoginVoThreadLocal.get();
    }

    public static void setUserLoginVo(UserLoginVo userLoginVo) {
        userLoginVoThreadLocal.set(userLoginVo);
    }

    public static Long getAdminId() {
        return adminIdThreadLocal.get();
    }

    public static void setAdminId(Long adminId) {
        adminIdThreadLocal.set(adminId);
    }

    public static AdminLoginVo getAdminLoginVo() {
        return adminLoginVoThreadLocal.get();
    }

    public static void setAdminLoginVo(AdminLoginVo adminLoginVo) {
        adminLoginVoThreadLocal.set(adminLoginVo);
    }
}
