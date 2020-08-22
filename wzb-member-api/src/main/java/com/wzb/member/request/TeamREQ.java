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
public class TeamREQ implements Serializable {
    /**
     * 组名
     */
    private String name;

    /**
     * 负责人姓名
     */
    private String linkman;

    /**
     * 手机号码
     */
    private String mobile;
}
