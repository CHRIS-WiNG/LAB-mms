package com.wzb.member.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author wzb
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_student")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学生记录id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 学号
     */
    private String stuNum;

    /**
     * 姓名
     */
    private String name;

    /**
     * email
     */
    private String email;

    /**
     * 本月打卡数，被事件每月清空
     */
    private Integer clockinNum;

    /**
     * 生日
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 入学年份
     */
    private String entryYear;

    /**
     * 院系
     */
    private String college;

    /**
     * 是否毕业0否1是
     */
    private Integer isGraduate;

    /**
     * 学历1实习生，2本科，3硕士，4博士
     */
    private Integer level;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 学生关联的用户id
     */
    private Integer uid;

    /**
     * 学生住址
     */
    private String address;
}
