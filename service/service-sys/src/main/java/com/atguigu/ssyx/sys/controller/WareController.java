package com.atguigu.ssyx.sys.controller;


import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.sys.Ware;
import com.atguigu.ssyx.sys.service.WareService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 仓库表 前端控制器
 * </p>
 *
 * @author syh
 * @since 2023-08-18
 */
@RestController
@RequestMapping("/admin/sys/ware")
@Api(tags = "仓库接口")

public class WareController {

    @Autowired
    private WareService wareService;

    @ApiOperation("获取所有仓库")
    @GetMapping("/findAllList")
    public Result findAllList(){
        List<Ware> data = wareService.list();
        return Result.ok(data);
    }
}

