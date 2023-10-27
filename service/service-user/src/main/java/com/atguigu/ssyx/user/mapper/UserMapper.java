package com.atguigu.ssyx.user.mapper;

import com.atguigu.ssyx.model.user.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author syh
 * @since 2023-09-12
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
