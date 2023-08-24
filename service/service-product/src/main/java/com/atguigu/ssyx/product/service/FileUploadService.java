package com.atguigu.ssyx.product.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {
    String fileUpload(MultipartFile file) throws IOException;
}
