package com.wzb.member.util.oss.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.wzb.member.util.oss.FileState;
import com.wzb.member.util.oss.FileStateEnum;
import com.wzb.member.util.oss.ImageHolder;
import com.wzb.member.util.oss.OssService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.Random;

/**
 * @author Zebin Wang
 * @Title:
 * @Package
 * @Description: oss上传管理Service实现类
 */
@Service
public class OssServiceImpl implements OssService {

    @Value("${aliyun.oss.bucketName}")
    private String ALIYUN_OSS_BUCKET_NAME;
    @Value("${aliyun.oss.endpoint}")
    private String ALIYUN_OSS_ENDPOINT;

//    @Value("${aliyun.oss.dir.prefix}")
//    private String ALIYUN_OSS_DIR_PREFIX;

    //图片URL过期时间为10年  3600l* 1000*24*365*10
    private final long EXPIRATION = 36001 * 1000 * 24 * 365 * 10;

    @Autowired
    private OSSClient ossClient;

    /**
     * 上传一张图片到阿里云OSS中
     *
     * @param file 需要上传的图片输入流
     * @return
     */
    @Override
    @Transactional
    public FileState addHeadImage(MultipartFile file, String prefix) {
        //上传图片到阿里云中并返回成功上传后的图片名
        ImageHolder holder = addHeadImageToOss(file, prefix);
        if (holder.getStateEnum().getState() > 0) {
            //如果需要获取上传后图片的URL,则调用该方法即可
             String imgUrl = getImageUrl(holder.getFileName(),prefix);
             return FileState.ok(holder.getStateEnum().getStateInfo(), imgUrl);
        } else {
            return FileState.fail(holder.getStateEnum().getStateInfo());
        }
    }


    /**
     * 获取成功上传后文件的url
     *
     * @param fileName 文件名
     * @param dirPre   上传文件的所在路径
     * @return
     */
    public String getImageUrl(String fileName, String dirPre) {
        String imgUrl = "";
        if (!StringUtils.isEmpty(fileName)) {
            String[] split = fileName.split("/");
            String key = dirPre + split[split.length - 1];
            Date expiration = new Date(System.currentTimeMillis() + EXPIRATION);
            //生成URL
            URL url = ossClient.generatePresignedUrl(ALIYUN_OSS_BUCKET_NAME, key, expiration);
            if (null != url) {
                imgUrl = url.toString();
            }
        }
        return "".equals(imgUrl) ? imgUrl : imgUrl.substring(0, imgUrl.indexOf("?"));
    }

    /**
     * 上传至OSS中并返回新生成的文件名
     *
     * @param file 需要上传的文件图片
     * @return
     */
    public ImageHolder addHeadImageToOss(MultipartFile file, String filePath) {
        String fileName = null;
        InputStream instream = null;
        try {
            //根据原始文件名随机生成新的文件名
            fileName = getRandomName(file.getOriginalFilename());
            instream = file.getInputStream();
            //创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(instream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getContentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            //上传文件
            PutObjectResult putResult = ossClient.putObject(ALIYUN_OSS_BUCKET_NAME, filePath + fileName, instream, objectMetadata);
        } catch (IOException e) {
            return new ImageHolder(FileStateEnum.UPLOAD_FILE_FAIL);
        } finally {
            try {
                if (null != instream) {
                    instream.close();
                }
            } catch (IOException e) {
                return new ImageHolder(FileStateEnum.UPLOAD_FILE_FAIL);
            }
        }
        return new ImageHolder(FileStateEnum.UPLOAD_FILE_SUCCESS, fileName);
    }

    /**
     * 随机生成文件名
     *
     * @param originalFileName 原始的文件名
     * @return
     */
    private String getRandomName(String originalFileName) {
        //获取文件的后缀名
        String subString = originalFileName.substring(originalFileName.lastIndexOf("."))
                .toLowerCase();
        Random random = new Random();
        return random.nextInt(10000) + System.currentTimeMillis() + subString;
    }

    /**
     * 判断OSS服务文件上传时文件的类型contentType
     *
     * @param fileNameExtension
     * @return
     */
    private String getContentType(String fileNameExtension) {
        if (fileNameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (fileNameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (fileNameExtension.equalsIgnoreCase(".jpeg") ||
                fileNameExtension.equalsIgnoreCase(".jpg") ||
                fileNameExtension.equalsIgnoreCase(".png")) {
            return "image/jpeg";
        }
        if (fileNameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (fileNameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (fileNameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (fileNameExtension.equalsIgnoreCase(".pptx") ||
                fileNameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (fileNameExtension.equalsIgnoreCase(".docx") ||
                fileNameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (fileNameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        return "image/jpg";
    }

}

