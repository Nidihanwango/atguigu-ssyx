package com.atguigu.ssyx.sys.controller;


import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.sys.Region;
import com.atguigu.ssyx.sys.service.RegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 地区表 前端控制器
 * </p>
 *
 * @author syh
 * @since 2023-08-18
 */
@RestController
@RequestMapping("/admin/sys/region")
@Api(tags = "区域接口")
@CrossOrigin
public class RegionController {
    @Autowired
    private RegionService regionService;

    @ApiOperation("根据关键字查找区域")
    @GetMapping("findRegionByKeyword/{keyword}")
    public Result findRegionByKeyword(@PathVariable("keyword") String keyword){
        List<Region> data = regionService.findRegionByKeyword(keyword);
        return Result.ok(data);
    }
}

