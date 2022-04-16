package com.summer.controller;

import com.summer.service.UploadService;
import com.summer.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Summer
 * @since 2022/4/16 23:02
 */
@RestController
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    public R upload(MultipartFile img) {
        return uploadService.upload(img);
    }


}
