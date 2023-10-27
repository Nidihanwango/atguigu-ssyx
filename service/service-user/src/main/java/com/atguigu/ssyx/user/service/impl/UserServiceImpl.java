package com.atguigu.ssyx.user.service.impl;


import com.atguigu.ssyx.model.user.User;
import com.atguigu.ssyx.user.mapper.UserMapper;
import com.atguigu.ssyx.user.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author syh
 * @since 2023-09-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
