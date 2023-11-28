package com.atguigu.ssyx.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.ssyx.common.auth.AuthContextHolder;
import com.atguigu.ssyx.common.constant.RedisConst;
import com.atguigu.ssyx.common.exception.SsyxException;
import com.atguigu.ssyx.common.result.ResultCodeEnum;
import com.atguigu.ssyx.common.utils.JwtHelper;
import com.atguigu.ssyx.enums.UserType;
import com.atguigu.ssyx.model.user.Leader;
import com.atguigu.ssyx.model.user.User;
import com.atguigu.ssyx.model.user.UserDelivery;
import com.atguigu.ssyx.user.mapper.LeaderMapper;
import com.atguigu.ssyx.user.mapper.UserDeliveryMapper;
import com.atguigu.ssyx.user.mapper.UserMapper;
import com.atguigu.ssyx.user.service.WeiXinApiService;
import com.atguigu.ssyx.user.utils.ConstantPropertiesUtil;
import com.atguigu.ssyx.user.utils.HttpClientUtils;
import com.atguigu.ssyx.vo.user.LeaderAddressVo;
import com.atguigu.ssyx.vo.user.UserLoginVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class WeiXinApiServiceImpl implements WeiXinApiService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LeaderMapper leaderMapper;
    @Autowired
    private UserDeliveryMapper userDeliveryMapper;

    @Override
    public Map<String, Object> wxLogin(String code) {
        // 1.判断参数是否为空
        if (StringUtils.isEmpty(code)) {
            throw new SsyxException(ResultCodeEnum.ILLEGAL_CALLBACK_REQUEST_ERROR);
        }
        Map<String, Object> map = new HashMap<>();
        // 1.携带code appId appSecret 向微信服务器发送请求
        StringBuffer sb = new StringBuffer();
        sb.append("https://api.weixin.qq.com/sns/jscode2session")
                .append("?appid=%s")
                .append("&secret=%s")
                .append("&js_code=%s")
                .append("&grant_type=authorization_code");
        String url = String.format(sb.toString(), ConstantPropertiesUtil.WX_OPEN_APP_ID, ConstantPropertiesUtil.WX_OPEN_APP_SECRET, code);
        String result = null;
        try {
            result = HttpClientUtils.get(url);
        } catch (Exception e) {
            throw new SsyxException(ResultCodeEnum.FETCH_ACCESSTOKEN_FAILD);
        }
        // 2.解析微信服务器返回的数据 拿到session_key 和 open_id
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (jsonObject.getString("errcode") != null) {
            throw new SsyxException(ResultCodeEnum.FETCH_ACCESSTOKEN_FAILD);
        }
        String sessionKey = jsonObject.getString("session_key");
        String openid = jsonObject.getString("openid");
        // 3.判断该用户是否是新用户
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getOpenId, openid));
        if (user == null) {
            // 3.1 创建新用户并存入数据库
            user = new User();
            user.setOpenId(openid);
            user.setNickName(openid);
            user.setPhotoUrl("");
            user.setUserType(UserType.USER);
            user.setIsNew(0);
            userMapper.insert(user);
        }
        // 4.根据open_id获取提货点和团长数据
        LeaderAddressVo leaderAddressVo = getLeaderAddressVoByUserId(user.getId());

        // 5.使用jwt编码得到token
        String token = JwtHelper.createToken(user.getId(), user.getNickName());

        UserLoginVo userLoginVo = getUserLoginVo(user.getId());
        redisTemplate.opsForValue().set(RedisConst.USER_LOGIN_KEY_PREFIX + user.getId(), userLoginVo, RedisConst.USERKEY_TIMEOUT, TimeUnit.DAYS);
        // 返回 用户 提货点 团长 token
        map.put("user", user);
        map.put("leaderAddressVo", leaderAddressVo);
        map.put("token", token);

        return map;
    }

    @Override
    public void updateUser(User user) {
        User user1 = userMapper.selectById(AuthContextHolder.getUserId());
        //把昵称更新为微信用户
        user1.setNickName(user.getNickName().replaceAll("[ue000-uefff]", "*"));
        user1.setPhotoUrl(user.getPhotoUrl());
        userMapper.updateById(user1);
    }

    private UserLoginVo getUserLoginVo(Long userId) {
        UserLoginVo userLoginVo = new UserLoginVo();
        User user = userMapper.selectById(userId);
        userLoginVo.setNickName(user.getNickName());
        userLoginVo.setUserId(userId);
        userLoginVo.setPhotoUrl(user.getPhotoUrl());
        userLoginVo.setOpenId(user.getOpenId());
        userLoginVo.setIsNew(user.getIsNew());

        //如果是团长获取当前前团长id与对应的仓库id
        if (user.getUserType() == UserType.LEADER) {
//            LambdaQueryWrapper<Leader> queryWrapper = new LambdaQueryWrapper<>();
//            queryWrapper.eq(Leader::getUserId, userId);
//            queryWrapper.eq(Leader::getCheckStatus, 1);
//            Leader leader = leaderMapper.selectOne(queryWrapper);
//            if(null != leader) {
//                userLoginVo.setLeaderId(leader.getId());
//                Long wareId = regionFeignClient.getWareId(leader.getRegionId());
//                userLoginVo.setWareId(wareId);
//            }
        } else {
            //如果是会员获取当前会员对应的仓库id
            LambdaQueryWrapper<UserDelivery> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(UserDelivery::getUserId, userId);
            queryWrapper.eq(UserDelivery::getIsDefault, 1);
            UserDelivery userDelivery = userDeliveryMapper.selectOne(queryWrapper);
            if (null != userDelivery) {
                userLoginVo.setLeaderId(userDelivery.getLeaderId());
                userLoginVo.setWareId(userDelivery.getWareId());
            } else {
                userLoginVo.setLeaderId(1L);
                userLoginVo.setWareId(1L);
            }
        }
        return userLoginVo;
    }

    @Override
    public LeaderAddressVo getLeaderAddressVoByUserId(Long id) {
        UserDelivery userDelivery = userDeliveryMapper.selectOne(new LambdaQueryWrapper<UserDelivery>().eq(UserDelivery::getUserId, id).eq(UserDelivery::getIsDefault, 1));
        if (userDelivery == null) {
            return null;
        }
        Leader leader = leaderMapper.selectById(userDelivery.getLeaderId());
        LeaderAddressVo leaderAddressVo = new LeaderAddressVo();
        BeanUtils.copyProperties(leader, leaderAddressVo);
        leaderAddressVo.setUserId(id);
        leaderAddressVo.setLeaderId(leader.getId());
        leaderAddressVo.setLeaderName(leader.getName());
        leaderAddressVo.setLeaderPhone(leader.getPhone());
        leaderAddressVo.setWareId(userDelivery.getWareId());
        leaderAddressVo.setStorePath(leader.getStorePath());
        return leaderAddressVo;
    }
}
