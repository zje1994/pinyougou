package com.pinyougou.manager.controller;

import entity.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import util.FastDFSClient;

/**
 * Created by ZJE on 2018/12/12
 */

@RestController
@RequestMapping("/upload")
public class UploadController {

    @Value("${FILE_SERVER_URL}")
    private String FILE_SERVER_URL;

    @RequestMapping("/uploadFile")
    public Result uploadFile(MultipartFile file){

        //基于FastDFSClient工具类完成文件上传
        try {
            //获取文件源名称
            String originalFilename = file.getOriginalFilename();
            //获取拓展名
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);


            FastDFSClient fastDFSClient = new FastDFSClient("classpath:config/fdfs_client.conf");
            String filePath = fastDFSClient.uploadFile(file.getBytes(), extName);
            //图片地址
            String fileUrl=FILE_SERVER_URL+filePath;
            return new Result(true,fileUrl);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"上传失败");
        }


    }





}
