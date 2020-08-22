package com.wzb.member.util.oss;

import com.wzb.member.base.Result;
import lombok.Data;

/**
 * @author Zebin Wang
 * @Title:
 * @Package
 * @Description:
 */
@Data
public class FileState {

    private int state;

    private String stateInfo;

    private String url;

    public FileState(int state, String stateInfo, String url) {
        this.state = state;
        this.stateInfo = stateInfo;
        this.url = url;
    }

    public static FileState ok(String stateInfo, String url) {
        return new FileState(FileStateEnum.UPLOAD_FILE_SUCCESS.getState(), stateInfo, url);
    }

    public static FileState fail(String stateInfo) {
        return new FileState(FileStateEnum.GET_FILE_FAIL.getState(), stateInfo, null);
    }
}
