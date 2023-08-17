package com.atguigu.ssyx.acl.service;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.acl.Admin;
import com.atguigu.ssyx.vo.acl.AdminQueryVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface AdminService extends IService<Admin> {
    Result pageList(Integer page, Integer limit, AdminQueryVo vo);

    Map<String, Object> getRolesByAdminId(Long adminId);

    void doAssign(Long adminId, String roleId);
}
