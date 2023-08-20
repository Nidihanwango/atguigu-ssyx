package com.atguigu.ssyx.sys.controller;


import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.sys.RegionWare;
import com.atguigu.ssyx.sys.service.RegionWareService;
import com.atguigu.ssyx.vo.sys.RegionWareQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 城市仓库关联表 前端控制器
 * </p>
 *
 * @author syh
 * @since 2023-08-18
 */
@RestController
@RequestMapping("/admin/sys/regionWare")
@Api(tags = "开通区域接口")
@CrossOrigin
public class RegionWareController {

    @Autowired
    private RegionWareService regionWareService;

    @GetMapping("/{page}/{limit}")
    @ApiOperation("开通区域列表")
    public Result getPageList(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit, @RequestParam(value = "keyword", required = false) String key){
        IPage<RegionWare> pageList = regionWareService.getPageList(page, limit, key);
        return Result.ok(pageList);
    }

    @GetMapping("/get/{id}")
    @ApiOperation("根据id获取开通区域")
    public Result getById(@PathVariable("id") Long id) {
        RegionWare regionWare = regionWareService.getById(id);
        return Result.ok(regionWare);
    }

    @PostMapping("/save")
    @ApiOperation("新增开通区域")
    public Result save(@RequestBody RegionWare regionWare){
        regionWareService.saveRegionWare(regionWare);
        return Result.ok(null);
    }

    @ApiOperation("删除开通区域")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable("id") Long id){
        regionWareService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation("更新开通区域状态")
    @PostMapping("/updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable("id") Long id, @PathVariable("status") Integer status){
        regionWareService.updateStatus(id, status);
        return Result.ok(null);
    }

}

