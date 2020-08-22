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
public class AccountREQ implements Serializable {
    /**
     * 帐号
     */
    private String username;

    /**
     * 姓名
     */
    private String name;
}
