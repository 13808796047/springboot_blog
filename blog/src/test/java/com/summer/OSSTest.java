package com.summer;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Summer
 * @since 2022/4/16 22:39
 */
@SpringBootTest
public class OSSTest {
    private String assessKey;
    private String secretKey;
    private String bucket;

    @Test
    public void test() {
//构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
//...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        String accessKey = "to5PmQwPiuUxgSUENex2ws9XfJ6U7kfJ4DdTkIFo";
        String secretKey = "bdcL8IHO8vEwRK_SWh8lNJ0gbvSO-bP5UDf8oH1m";
        String bucket = "csyimg";
//如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "C:\\Users\\Mars\\Desktop\\SGBlog\\笔记\\img\\image-20220228230512598.png";
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "sg.png";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
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
        }
    }
}
