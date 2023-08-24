package com.atguigu.ssyx.product.service.impl;

import com.atguigu.ssyx.product.service.FileUploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    private static final String FILEDIR = "D:/nginx/nginx-1.24.0/html/static/images/";
    @Override
    public String fileUpload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        String dateDir = LocalDate.now().toString().replaceAll("-", "/") + "/";

        File dir = new File(FILEDIR + dateDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        UUID uuid = UUID.randomUUID();
        String filePath = FILEDIR + dateDir + uuid + suffix;
        file.transferTo(new File(filePath));

        return "http://localhost:9001/static/images/" + dateDir + uuid + suffix;
    }
}
