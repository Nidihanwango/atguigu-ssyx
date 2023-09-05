package com.atguigu.ssyx.product.controller;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.product.service.FileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Api("文件上传接口")

@RequestMapping("/admin/product")
@RestController
public class FileUploadController {
    @Autowired
    private FileUploadService fileUploadService;

    @ApiOperation("上传文件")
    @PostMapping("/fileUpload")
    public Result fileUpload(MultipartFile file) throws IOException {
        return Result.ok(fileUploadService.fileUpload(file));
    }
}
