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
public class StudentREQ implements Serializable {
    /**
     * 学号
     */
    private String stuNum;

    /**
     * 姓名
     */
    private String name;

    /**
     * 学历1实习生，2本科，3硕士，4博士
     */
    private Integer level;

    /**
     * 入学年份
     */
    private String entryYear;

    /**
     * 是否毕业0否1是
     */
    private Integer isGraduate;

    /**
     * 按照某列排序使用，需要排序的列名，默认为null
     */
    private String orderBy;

    /**
     * 排序方式，asc升序，desc降序，null不排序
     */
    private String order;
}
