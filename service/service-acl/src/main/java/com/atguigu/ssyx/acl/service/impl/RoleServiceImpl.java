package com.atguigu.ssyx.acl.service.impl;

import com.atguigu.ssyx.model.acl.Role;
import com.atguigu.ssyx.acl.mapper.RoleMapper;
import com.atguigu.ssyx.acl.service.RoleService;
import com.atguigu.ssyx.vo.acl.RoleQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Override
    public IPage<Role> pageConditionQuery(Integer page, Integer limit, RoleQueryVo vo) {
        IPage<Role> rolePage = new Page<>(page, limit);
        QueryWrapper<Role> wrapper = new QueryWrapper<Role>().like("role_name", vo.getRoleName());
        IPage<Role> result = page(rolePage, wrapper);
        return result;
    }
}
