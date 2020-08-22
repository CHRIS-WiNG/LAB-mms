package com.wzb.member.util.oss;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Zebin Wang
 * @Title:
 * @Package
 * @Description:
 */
@Data
public class ImageHolder {
    private FileStateEnum StateEnum;
    private String fileName;
    private MultipartFile multipartFile;
    private List<MultipartFile> list;

    //成功的构造器
    public ImageHolder(FileStateEnum stateEnum, MultipartFile multipartFile) {
        StateEnum = stateEnum;
        this.multipartFile = multipartFile;
    }
    public ImageHolder(FileStateEnum stateEnum, String fileName) {
        StateEnum = stateEnum;
        this.fileName = fileName;
    }

    //失败的构造器
    public ImageHolder(FileStateEnum stateEnum) {
        StateEnum = stateEnum;
    }
}
