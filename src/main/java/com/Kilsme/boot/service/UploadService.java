package com.Kilsme.boot.service;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;     // ✅ 修改：javax → jakarta

import java.io.InputStream;

@Service
public class UploadService {

    @Value("${qiniu.ak}")
    private String accessKey;

    @Value("${qiniu.sk}")
    private String secretKey;

    @Value("${qiniu.bucket}")
    private String bucket;

    @Value("${qiniu.domain}")
    private String domain;

    private UploadManager uploadManager;

    @PostConstruct
    public void init() {
        Configuration cfg = new Configuration(Region.huanan()); // 根据你的 bucket 区域修改
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;
        uploadManager = new UploadManager(cfg);
    }

    public String upload(InputStream in, String filename) {
        try {
            // 生成唯一文件名：时间戳 + 后缀
            String suffix = filename.substring(filename.lastIndexOf(".") + 1);
            String key = System.currentTimeMillis() + "." + suffix;
            // 每次上传都生成新 token（有效期1小时）
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            // 执行上传
            Response res = uploadManager.put(in, key, upToken, null, null);
            if (res.isOK()) {
                return domain + "/" + key; // 返回可访问的 URL
            } else {
                System.err.println("Upload failed: " + res.bodyString());
            }
        } catch (QiniuException e) {
            e.printStackTrace();
            if (e.response != null) {
                System.err.println("Qiniu Error: " + e.response.toString());
            }
        }
        return null;
    }
}