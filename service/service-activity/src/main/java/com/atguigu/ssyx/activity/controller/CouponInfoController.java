package com.atguigu.ssyx.activity.controller;


import com.atguigu.ssyx.activity.service.CouponInfoService;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.activity.CouponInfo;
import com.atguigu.ssyx.vo.activity.CouponRuleVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 优惠券信息 前端控制器
 * </p>
 *
 * @author syh
 * @since 2023-09-05
 */
@RestController
@RequestMapping("/admin/activity/couponInfo")

@Api(tags = "优惠券接口")
public class CouponInfoController {

    @Autowired
    private CouponInfoService couponInfoService;

    @ApiOperation("分页查询")
    @GetMapping("/{curPage}/{size}")
    public Result getPageList(@PathVariable("curPage") Integer curPage, @PathVariable("size") Integer size) {
        IPage<CouponInfo> pageParam = new Page<>(curPage, size);
        IPage<CouponInfo> pageModel = couponInfoService.getPageList(pageParam);
        return Result.ok(pageModel);
    }

    @ApiOperation("根据id查询")
    @GetMapping("/get/{id}")
    public Result getById(@PathVariable("id") Long id){
        CouponInfo model = couponInfoService.getById(id);
        model.setCouponTypeString(model.getCouponType().getComment());
        if (model.getRangeType() != null) {
            model.setRangeTypeString(model.getRangeType().getComment());
        }
        return Result.ok(model);
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public Result save(@RequestBody CouponInfo couponInfo) {
        couponInfoService.save(couponInfo);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改")
    @PutMapping("update")
    public Result updateById(@RequestBody CouponInfo couponInfo) {
        couponInfoService.updateById(couponInfo);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除优惠券")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable String id) {
        couponInfoService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation(value="根据id列表删除优惠券")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<String> idList){
        couponInfoService.removeByIds(idList);
        return Result.ok(null);
    }

    @ApiOperation(value = "获取优惠券信息")
    @GetMapping("findCouponRuleList/{id}")
    public Result findActivityRuleList(@PathVariable Long id) {
        return Result.ok(couponInfoService.findCouponRuleList(id));
    }

    @ApiOperation(value = "新增活动")
    @PostMapping("saveCouponRule")
    public Result saveCouponRule(@RequestBody CouponRuleVo couponRuleVo) {
        couponInfoService.saveCouponRule(couponRuleVo);
        return Result.ok(null);
    }
}

