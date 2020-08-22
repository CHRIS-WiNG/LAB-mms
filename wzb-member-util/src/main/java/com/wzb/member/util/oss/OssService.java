package com.wzb.member.util.oss;

import org.springframework.web.multipart.MultipartFile;
/**
 * @author Zebin Wang
 * @Title:
 * @Package
 * @Description: oss上传管理Service
 */
public interface OssService {
    FileState addHeadImage(MultipartFile file, String prefix);
}

