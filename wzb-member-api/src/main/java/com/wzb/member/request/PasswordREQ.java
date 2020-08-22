package com.wzb.member.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Zebin Wang
 * @Title:
 * @Package
 * @Description:
 */
@Data
public class PasswordREQ implements Serializable {

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 原密码或新密码
     */
    private String password;
}
