package com.atguigu.ssyx.index.controller;

import com.atguigu.ssyx.common.auth.AuthContextHolder;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.index.service.IndexService;
import com.atguigu.ssyx.model.product.Category;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/home")
@Api(tags = "首页接口")
public class IndexApiController {

    @Autowired
    private IndexService indexService;

    @ApiOperation("获取首页数据")
    @GetMapping("/index")
    public Result getIndexModel(HttpServletRequest request){
        Long userId = AuthContextHolder.getUserId();
        return Result.ok(indexService.getIndexModel(userId));
    }

    @ApiOperation("获取所有分类")
    @GetMapping("/category")
    public Result getAllCategory() {
        List<Category> categoryList = indexService.getAllCategory();
        return Result.ok(categoryList);
    }
}
