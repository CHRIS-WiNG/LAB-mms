package com.wzb.member.util.oss;

/**
 * @author Zebin Wang
 * @Title:
 * @Package
 * @Description:
 */
public enum FileStateEnum {
    GET_FILE_FAIL(-1,"获取文件流失败"),GET_FILE_SUCCESS(1,"成功获取文件流ccc"),
    NULL_FILE(-1,"上传文件为空"),MAX_SIZE_FILE(-1,"上传图片大小不能超过10M"),
    UPLOAD_FILE_FAIL(-1,"上传文件失败"),UPLOAD_FILE_SUCCESS(1,"上传文件成功"),
    DELETE_FILE_FAIL(-1,"删除文件失败");

    private int state;
    private String stateInfo;

    FileStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }
}

