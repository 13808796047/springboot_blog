package com.summer.service;

import com.summer.utils.R;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Summer
 * @since 2022/4/16 23:05
 */
public interface UploadService {
    R upload(MultipartFile img);
}
