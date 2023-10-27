package com.atguigu.ssyx.user.controller;


import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.user.service.WeiXinApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/user/weixin")
@Api(tags = "微信小程序后端接口")
public class WeiXinApiController {

    @Autowired
    private WeiXinApiService weiXinApiService;

    @ApiOperation("微信用户登录")
    @GetMapping("/wxLogin/{code}")
    public Result wxLogin(@PathVariable String code){
        Map<String, Object> model = weiXinApiService.wxLogin(code);
        return Result.ok(model);
    }
}

