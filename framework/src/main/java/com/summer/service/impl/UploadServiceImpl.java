package com.summer.service.impl;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.summer.enums.ResponseStatus;
import com.summer.exception.SystemException;
import com.summer.service.UploadService;
import com.summer.utils.PathUtils;
import com.summer.utils.R;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Summer
 * @since 2022/4/16 23:06
 */
@Service
@Data
@ConfigurationProperties(prefix = "oss")
public class UploadServiceImpl implements UploadService {
    private String accessKey;
    private String secretKey;
    private String bucket;

    @Override
    public R upload(MultipartFile img) {
        //判断文件类型或者文件大小
        String originalFilename = img.getOriginalFilename();
        if (!originalFilename.endsWith(".png")) {
            throw new SystemException(ResponseStatus.HTTP_STATUS_500);
        }
        String filePath = PathUtils.generateFilePath(originalFilename);
        String url = uploadOss(img, filePath);
        return R.success(url);
    }

    private String uploadOss(MultipartFile img, String filePath) {
//构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
//...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
//        String accessKey = "to5PmQwPiuUxgSUENex2ws9XfJ6U7kfJ4DdTkIFo";
//        String secretKey = "bdcL8IHO8vEwRK_SWh8lNJ0gbvSO-bP5UDf8oH1m";
//        String bucket = "csyimg";
//如果是Windows情况下，格式是 D:\\qiniu\\test.png

//默认不指定key的情况下，以文件内容的hash值作为文件名

        String key = filePath;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            InputStream inputStream = img.getInputStream();
            Response response = uploadManager.put(inputStream, key, upToken, null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);

        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "csyimg.s3-cn-east-1.qiniucs.com" + key;
    }
}
