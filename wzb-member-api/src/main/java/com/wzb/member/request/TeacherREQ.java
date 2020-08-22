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
public class TeacherREQ implements Serializable {
    /**
     * 教工号
     */
    private String tchNum;

    /**
     * 姓名
     */
    private String name;

    /**
     * 职级
     */
    private Integer level;

    /**
     * 所在组号
     */
    private Integer teamId;
}
