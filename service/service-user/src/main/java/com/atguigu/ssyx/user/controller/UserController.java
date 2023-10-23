package com.atguigu.ssyx.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.ssyx.common.constant.RedisConst;
import com.atguigu.ssyx.common.exception.SsyxException;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.common.result.ResultCodeEnum;
import com.atguigu.ssyx.common.utils.JwtHelper;
import com.atguigu.ssyx.enums.UserType;
import com.atguigu.ssyx.model.user.User;
import com.atguigu.ssyx.user.service.UserService;
import com.atguigu.ssyx.user.utils.HttpClientUtils;
import com.atguigu.ssyx.vo.user.LeaderAddressVo;
import com.atguigu.ssyx.vo.user.UserLoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/user/login")
@Api(tags = "用户管理接口")
public class UserController {
    @Value("${wx.open.app_id}")
    private String appId;

    @Value("${wx.open.app_secret}")
    private String appSecret;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("微信登录")
    @GetMapping("/wxlogin/{code}")
    public Result wxlogin(@PathVariable String code) {
        // 1.校验参数
        if (!StringUtils.isEmpty(code)) {
            throw new SsyxException(ResultCodeEnum.ILLEGAL_CALLBACK_REQUEST_ERROR);
        }
        // 2.拼接url code appid appSecret
        String url = "https://api.weixin.qq.com/sns/jscode2session" +
                "?appid=" + appId +
                "&appSecret=" + appSecret +
                "&js_code=" + code +
                "&grant_type=authorization_code";
        // 3.通过httpclient工具发送登录请求到微信平台
        String response = null;
        try {
            response = HttpClientUtils.get(url);
        } catch (Exception e) {
            throw new SsyxException(ResultCodeEnum.FETCH_ACCESSTOKEN_FAILD);
        }
        // 4.解析响应数据获取openid和session_key
        JSONObject jsonObject = JSONObject.parseObject(response);
        if (jsonObject.getString("errcode") != null) {
            throw new SsyxException(ResultCodeEnum.FETCH_ACCESSTOKEN_FAILD);
        }
        String openid = jsonObject.getString("openid");
        String sessionKey = jsonObject.getString("session_key");
        // 5.通过openid判断用户是否第一次登录,如是,将用户信息存入数据库,否则,获取用户信息
        User user = userService.getByOpenid(openid);
        if (user == null) {
            user = new User();
            user.setOpenId(openid);
            user.setNickName(openid);
            user.setPhotoUrl("");
            user.setUserType(UserType.USER);
            user.setIsNew(0);
            userService.save(user);
        }
        // 6.通过jwt将userid和username封装到token中
        String token = JwtHelper.createToken(user.getId(), user.getNickName());
        // 7.获取用户提货点和团长信息
        LeaderAddressVo leaderAddressVo = userService.getLeaderAddressVoByUserId(user.getId());
        // 8.将用户登录信息存入redis,并设置过期时间
        UserLoginVo userLoginVo = userService.getUserLoginVoByUserId(user.getId());
        redisTemplate.opsForValue().set(RedisConst.USER_KEY_PREFIX + user.getId(), userLoginVo, RedisConst.USERKEY_TIMEOUT, TimeUnit.DAYS);
        // 9.将用户信息,提货点,团长信息,token返回前端
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("user", user);
        map.put("leaderAddressVo", leaderAddressVo);
        return Result.ok(map);
    }
}
